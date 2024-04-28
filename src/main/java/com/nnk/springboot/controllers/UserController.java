package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/user")
@Tag(name = "User", description = "Manages operations related to users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/list")
    public String home(Model model)
    {
        Iterable<User> userList = userService.getUsers();
        List<UserDTO> users = StreamSupport.stream(userList.spliterator(), false)
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(user.getId());
                    userDTO.setFullname(user.getFullname());
                    userDTO.setUsername(user.getUsername());
                    userDTO.setRole(user.getRole());
                    return userDTO;
                })
                .toList();

        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "user/add";
    }

    @PostMapping("/validate")
    @ResponseBody
    public RedirectView validate(@Valid UserDTO userDTO, BindingResult result) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User user = User.builder()
                    .fullname(userDTO.getFullname())
                    .username(userDTO.getUsername())
                    .password(encoder.encode(userDTO.getPassword()))
                    .role(userDTO.getRole())
                    .build();

            userService.save(user);
            return new RedirectView("/user/list");
        }
        return new RedirectView("/user/add");
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUser(id);
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getFullname(),
                user.getUsername(),
                "",
                user.getRole()
        );

        model.addAttribute("userDTO", userDTO);
        return "user/update";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public RedirectView updateUser(@PathVariable("id") Integer id, @Valid UserDTO userDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("/user/update/{id}");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id(id)
                .fullname(userDTO.getFullname())
                .username(userDTO.getUsername())
                .password(encoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole())
                .build();

        userService.save(user);
        return new RedirectView("/user/list");
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public RedirectView deleteUser(@PathVariable("id") Integer id) {
        User user = userService.getUser(id);
        userService.delete(user);
        return new RedirectView("/user/list");
    }

}
