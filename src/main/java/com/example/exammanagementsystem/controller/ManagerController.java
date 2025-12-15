package com.example.exammanagementsystem.controller;

import com.example.exammanagementsystem.dto.user.UserResponseDto;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<List<UserResponseDto>> seeAllUsers() {
        List<UserResponseDto> allUser = userService.seeAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(allUser);
    }
}
