package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.services.impl.UserServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void testHome() throws Exception {
        List<User> userList = new ArrayList<>();
        User user1 = new User(
                1,
                "johndoe",
                "$2a$10$bFu2cyDMX.eBmZ7qR6npi.ij.37vHrHUOXSJrfT8PdUbb9X0hpCpW",
                "John Doe",
                "USER"
                );
        User user2 = new User(
                2,
                "janesmith",
                "$2a$10$bFu2cyDMX.eBmZ7qR6npi.ij.37vHrHUOXSJrfT8PdUbb9X0hpCpW",
                "Jane Smith",
                "USER"
        );
        userList.add(user1);
        userList.add(user2);

        when(userService.getUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.view().name("user/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.model().attribute("users", Matchers.everyItem(Matchers.hasProperty("id"))))
                .andExpect(MockMvcResultMatchers.model().attribute("users", Matchers.everyItem(Matchers.hasProperty("fullname"))))
                .andExpect(MockMvcResultMatchers.model().attribute("users", Matchers.everyItem(Matchers.hasProperty("username"))))
                .andExpect(MockMvcResultMatchers.model().attribute("users", Matchers.everyItem(Matchers.hasProperty("role"))))
                .andReturn();

        verify(userService, times(1)).getUsers();
    }

    @Test
    public void testGetAddUserPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("userDTO"))
                .andExpect(MockMvcResultMatchers.model().attribute("userDTO", Matchers.instanceOf(UserDTO.class)));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFullname("John Doe");
        user.setUsername("johndoe");
        user.setRole("USER");

        when(userService.getUser(1)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("userDTO"))
                .andExpect(MockMvcResultMatchers.model().attribute("userDTO", Matchers.hasProperty("id", Matchers.equalTo(1))))
                .andExpect(MockMvcResultMatchers.model().attribute("userDTO", Matchers.hasProperty("fullname", Matchers.equalTo("John Doe"))))
                .andExpect(MockMvcResultMatchers.model().attribute("userDTO", Matchers.hasProperty("username", Matchers.equalTo("johndoe"))))
                .andExpect(MockMvcResultMatchers.model().attribute("userDTO", Matchers.hasProperty("role", Matchers.equalTo("USER"))))
                .andExpect(MockMvcResultMatchers.view().name("user/update"))
                .andReturn();
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFullname("John Doe");
        user.setUsername("johndoe");
        user.setRole("USER");

        when(userService.getUser(1)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));

        verify(userService, times(1)).delete(user);
    }
}
