package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.dto.register.RegisterRequestDto;
import com.example.exammanagementsystem.dto.register.RegisterResponseDto;
import com.example.exammanagementsystem.model.RegisterStatus;
import com.example.exammanagementsystem.model.Role;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.repository.RoleRepository;
import com.example.exammanagementsystem.repository.UserRepository;
import com.example.exammanagementsystem.service.RoleService;
import com.example.exammanagementsystem.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,RoleRepository roleRepository) {
        super(userRepository);
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.roleRepository=roleRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto requestDto) {
        User user = User.builder()
                .username(requestDto.username())
                .firstname(requestDto.firstname())
                .lastname(requestDto.lastname())
                .email(requestDto.email())
                .password(passwordEncoder.encode(requestDto.password()))
                .registerStatus(RegisterStatus.PENDING)
                .build();


        Role role = roleRepository.findByName(requestDto.role()).orElseThrow();
        user.getRoles().add(role);
        saveOrUpdate(user);

        return new RegisterResponseDto("successfully registered");
    }
}
