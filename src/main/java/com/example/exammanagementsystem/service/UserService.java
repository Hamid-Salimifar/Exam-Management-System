package com.example.exammanagementsystem.service;


import com.example.exammanagementsystem.dto.register.RegisterDto;
import com.example.exammanagementsystem.dto.user.UserResponseDto;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends BaseService<User>{
    Optional<User> findByUsername(String username);

    String registerUser(RegisterDto requestDto);

    List<UserResponseDto> seeAllUser();

    User editUser(User user,List<Long> RoleIds);

    List<User> searchUsers(String username, String firstname, String lastname, String email, RoleName roleName);

    List<User> findByRole(RoleName roleName);


}
