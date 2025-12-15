package com.example.exammanagementsystem.dto.register;

import com.example.exammanagementsystem.model.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
        @NotBlank String username,

        @Email(message = "Email must be valid")
        @NotBlank
        String email,
        String firstname,
        String lastname,
        String password,
        RoleName role
) {
}
