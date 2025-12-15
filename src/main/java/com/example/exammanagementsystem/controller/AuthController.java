package com.example.exammanagementsystem.controller;

import com.example.exammanagementsystem.dto.login.LoginRequestDto;
import com.example.exammanagementsystem.dto.login.LoginResponseDto;
import com.example.exammanagementsystem.dto.register.RegisterRequestDto;
import com.example.exammanagementsystem.dto.register.RegisterResponseDto;
import com.example.exammanagementsystem.model.RegisterStatus;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.service.UserService;
import com.example.exammanagementsystem.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user=userService
                .findByUsername(request.username())
                .orElseThrow(()->new IllegalStateException("user Not found"));

        if(user.getRegisterStatus() != RegisterStatus.APPROVED){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("your account is " +user.getRegisterStatus().toString());
        }

        String token=jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new LoginResponseDto(token));

    }
}
