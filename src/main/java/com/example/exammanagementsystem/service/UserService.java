package com.example.exammanagementsystem.service;


import com.example.exammanagementsystem.model.User;

import java.util.Optional;

public interface UserService extends BaseService<User>{
    Optional<User> findByUsername(String username);
}
