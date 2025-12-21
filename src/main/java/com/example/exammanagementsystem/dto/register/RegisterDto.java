package com.example.exammanagementsystem.dto.register;

import com.example.exammanagementsystem.model.RoleName;


public record RegisterDto(
        String username,
        String email,
        String firstname,
        String lastname,
        String password,
        RoleName role
) {
}
