package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.LoginDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Tag(name = "Login", description = "Manages login operation")
public class LoginController {
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public RedirectView login(@Valid LoginDTO loginDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new RedirectView("/login");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/bidList/list");
    }

    @GetMapping("/error")
    public String error(Model model) {
        String errorMessage= "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        return "403";
    }
}
