package com.user.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(@NotBlank
                             String firstName,
                             @NotBlank
                             String lastName,
                             @NotBlank
                             @Email
                             String email,
                             @NotBlank
                             @Size(min = 6, max = 15)
                             String password,

                             @NotBlank
                             @Size(min = 6, max = 20)
                             String repeatedPassword) {
}
