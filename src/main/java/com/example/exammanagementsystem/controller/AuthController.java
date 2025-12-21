package com.example.exammanagementsystem.controller;


import com.example.exammanagementsystem.dto.register.RegisterDto;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @GetMapping("/register")
    public String goToRegisterPage(Model model,@RequestParam(required = false) String message){
        model.addAttribute("registerDto", new RegisterDto("", "", "", "","", RoleName.ROLE_STUDENT));
        model.addAttribute("RoleName",RoleName.values());
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterDto registerDto, Model model){

        if(registerDto.role() == RoleName.ROLE_MANAGER){
            String message = URLEncoder.encode("You cannot register as manager", StandardCharsets.UTF_8);
            return "redirect:/register?message="+message;
        }

        if(userService.findByUsername(registerDto.username()).isPresent()){
            String message = URLEncoder.encode("Username already exists!", StandardCharsets.UTF_8);
            return "redirect:/register?message="+message;
        }

        userService.registerUser(registerDto);

        String message = URLEncoder.encode("Successfully registered! Wait for approval.", StandardCharsets.UTF_8);
        return "redirect:/register?message="+message;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model ){

        if(error != null){
            model.addAttribute("errorMessage","Invalid credential or your account  is not approved yet");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage",
                    "You have been logged out successfully.");
        }
        return "login";
    }





}
