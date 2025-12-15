package com.example.exammanagementsystem.controller;

import com.example.exammanagementsystem.dto.register.RegisterRequestDto;
import com.example.exammanagementsystem.dto.register.RegisterResponseDto;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto requestDto){
        if(requestDto.role() == RoleName.ROLE_MANAGER){
            return ResponseEntity.badRequest().body(new RegisterResponseDto("you can not register as manager"));
        }
        if(userService.findByUsername(requestDto.username()).isPresent()){
            return ResponseEntity.badRequest().body(new RegisterResponseDto("username already exist!"));
        }
        RegisterResponseDto registerResponseDto = userService.registerUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponseDto);

    }
}
