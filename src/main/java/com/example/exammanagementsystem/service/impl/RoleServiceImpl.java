package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.Role;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.repository.RoleRepository;
import com.example.exammanagementsystem.service.RoleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    private final RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository=roleRepository;
    }

    @Override
    public Role findByName(RoleName name) {
       return roleRepository.findByName(name);
    }
}
