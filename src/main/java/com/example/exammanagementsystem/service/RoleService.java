package com.example.exammanagementsystem.service;

import com.example.exammanagementsystem.model.Role;
import com.example.exammanagementsystem.model.RoleName;

import java.util.Optional;

public interface RoleService extends BaseService<Role>{
    Optional<Role> findByName(RoleName name);
}
