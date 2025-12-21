package com.example.exammanagementsystem.bootstrap;

import com.example.exammanagementsystem.model.RegisterStatus;
import com.example.exammanagementsystem.model.Role;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.repository.RoleRepository;
import com.example.exammanagementsystem.repository.UserRepository;
import com.example.exammanagementsystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {



    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if(roleRepository.findByName(RoleName.ROLE_MANAGER).isEmpty()){
            Role managerRole = Role.builder()
                    .name(RoleName.ROLE_MANAGER)
                    .build();
            roleRepository.save(managerRole);
        }

        if(roleRepository.findByName(RoleName.ROLE_TEACHER).isEmpty()){
            Role teacherRole = Role.builder()
                    .name(RoleName.ROLE_TEACHER)
                    .build();
            roleRepository.save(teacherRole);
        }

        if(roleRepository.findByName(RoleName.ROLE_STUDENT).isEmpty()){
            Role studentRole = Role.builder()
                    .name(RoleName.ROLE_STUDENT)
                    .build();
            roleRepository.save(studentRole);
        }


        if(userRepository.findByUsername("manager").isEmpty()){
            User manager = User.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("manager"))
                    .firstname("manager")
                    .lastname("manager")
                    .registerStatus(RegisterStatus.APPROVED)
                    .email("manager@gmail.com")
                    .build();
            Role roleOfManager = roleRepository.findByName(RoleName.ROLE_MANAGER)
                    .orElseThrow(()->new IllegalStateException("Role_MANAGER not found!"));

            manager.getRoles().add(roleOfManager);
            userRepository.save(manager);
        }


    }
}
