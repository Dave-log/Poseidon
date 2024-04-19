package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.RegisterDTO;
import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
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

    @RequestMapping("/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("registerDTO", registerDTO);
        return "user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid RegisterDTO registerDTO, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User user = User.builder()
                    .fullname(registerDTO.getFullname())
                    .username(registerDTO.getUsername())
                    .password(encoder.encode(registerDTO.getPassword()))
                    .role(registerDTO.getRole())
                    .build();

            userService.save(user);
            model.addAttribute("users", userService.getUsers());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUser(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid RegisterDTO registerDTO,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .id(id)
                .fullname(registerDTO.getFullname())
                .username(registerDTO.getUsername())
                .password(encoder.encode(registerDTO.getPassword()))
                .role(registerDTO.getRole())
                .build();

        userService.save(user);
        model.addAttribute("users", userService.getUsers());
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUser(id);
        userService.delete(user);
        model.addAttribute("users", userService.getUsers());
        return "redirect:/user/list";
    }

}
