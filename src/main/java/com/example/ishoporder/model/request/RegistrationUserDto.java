package com.example.ishoporder.model.request;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationUserDto {
    @NotBlank(message = "username can't be blank")
    private String username;

    @NotBlank(message = "password can't be blank")
    private String password;

    @NotBlank(message = "confirmPassword can't be blank")
    private String confirmPassword;

    @Email
    private String email;
}