package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotBlank(message = "Username is mandatory!")
    String username;

    @NotBlank(message = "Password is mandatory!")
    String password;
}
