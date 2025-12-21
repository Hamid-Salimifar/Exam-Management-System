package com.example.exammanagementsystem.dto.user;

import com.example.exammanagementsystem.model.RegisterStatus;

public record UserResponseDto(
        Long id,
        String username,
        String firstname,
        String lastname,
        String email,
        RegisterStatus registerStatus
) {
}
