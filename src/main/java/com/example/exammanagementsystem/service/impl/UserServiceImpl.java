package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.repository.UserRepository;
import com.example.exammanagementsystem.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository=userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
