package com.diploma.ppc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Ім'я користувача обов'язкове")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "Email обов'язковий")
    @Email(message = "Некоректний формат email")
    private String email;

    @NotBlank(message = "Пароль обов'язковий")
    @Size(min = 6, message = "Пароль має містити щонайменше 6 символів")
    private String password;
}
