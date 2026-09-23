package com.diploma.ppc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Ім'я користувача обов'язкове")
    private String username;

    @NotBlank(message = "Пароль обов'язковий")
    private String password;
}
