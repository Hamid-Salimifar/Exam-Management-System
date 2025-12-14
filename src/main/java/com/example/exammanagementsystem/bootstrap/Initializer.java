package com.example.exammanagementsystem.bootstrap;

import com.example.exammanagementsystem.model.RegisterStatus;
import com.example.exammanagementsystem.model.Role;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.service.RoleService;
import com.example.exammanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if(roleService.findByName(RoleName.ROLE_MANAGER).isEmpty()){
            Role managerRole = Role.builder()
                    .name(RoleName.ROLE_MANAGER)
                    .build();
            roleService.saveOrUpdate(managerRole);
        }

        if(roleService.findByName(RoleName.ROLE_TEACHER).isEmpty()){
            Role teacherRole = Role.builder()
                    .name(RoleName.ROLE_TEACHER)
                    .build();
            roleService.saveOrUpdate(teacherRole);
        }

        if(roleService.findByName(RoleName.ROLE_STUDENT).isEmpty()){
            Role studentRole = Role.builder()
                    .name(RoleName.ROLE_STUDENT)
                    .build();
            roleService.saveOrUpdate(studentRole);
        }


        if(userService.findByUsername("manager").isEmpty()){
            User manager = User.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("manager"))
                    .firstname("manager")
                    .lastname("manager")
                    .registerStatus(RegisterStatus.APPROVED)
                    .email("manager@gmail.com")
                    .build();
            Role roleOfManager = roleService.findByName(RoleName.ROLE_MANAGER)
                    .orElseThrow(()->new IllegalStateException("Role_MANAGER not found!"));

            manager.getRoles().add(roleOfManager);
            userService.saveOrUpdate(manager);
        }


    }
}
