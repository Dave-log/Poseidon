package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.notFound.UserNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetUser_ExistingUser_ById() {
        // Arrange
        User user = new User();
        Integer id = 1;
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User existingUser = userService.getUser(id);

        // Assert
        assertEquals(id, existingUser.getId());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testGetUser_NonExistingUser_ById() {
        // Arrange
        Integer id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUser(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testGetUser_ExistingUser_ByUsername() {
        // Arrange
        User user = new User();
        String username = "johndoe";
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        User existingUser = userService.getUser(username);

        // Assert
        assertEquals(username, existingUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testGetUser_NonExistingUser_ByUsername() {
        // Arrange
        String username = "johndoe";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUser(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testGetUsers_ReturnsUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(User.builder().id(1).username("John").build());
        userList.add(User.builder().id(2).username("Alice").build());
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        Iterable<User> result = userService.getUsers();

        // Assert
        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(2, resultList.size());
        assertEquals("John", resultList.getFirst().getUsername());
        assertEquals("Alice", resultList.getLast().getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUsers_ReturnsEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Iterable<User> result = userService.getUsers();

        // Assert
        assertNotNull(result);
        List<User> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertTrue(resultList.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // Arrange
        User user = new User();

        // Act
        userService.save(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDelete() {
        // Arrange
        User user = new User();

        // Act
        userService.delete(user);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }
}
