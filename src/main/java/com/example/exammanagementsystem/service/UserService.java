package com.example.exammanagementsystem.service;


import com.example.exammanagementsystem.dto.register.RegisterRequestDto;
import com.example.exammanagementsystem.dto.register.RegisterResponseDto;
import com.example.exammanagementsystem.dto.user.UserResponseDto;
import com.example.exammanagementsystem.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User>{
    Optional<User> findByUsername(String username);

    RegisterResponseDto registerUser(RegisterRequestDto requestDto);

    List<UserResponseDto> seeAllUser();
}
