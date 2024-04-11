package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
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
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
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
