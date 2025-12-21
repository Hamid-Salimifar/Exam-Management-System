package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.dto.register.RegisterDto;
import com.example.exammanagementsystem.dto.user.UserResponseDto;
import com.example.exammanagementsystem.model.RegisterStatus;
import com.example.exammanagementsystem.model.Role;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.repository.RoleRepository;
import com.example.exammanagementsystem.repository.UserRepository;
import com.example.exammanagementsystem.search.UserSpecification;
import com.example.exammanagementsystem.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public String registerUser(RegisterDto requestDto) {
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

        return "successfully registered";
    }

    @Override
    public List<User> findByRole(RoleName roleName) {
        return userRepository.findByRoles_Name(roleName);
    }

    public List<User> searchUsers(String username, String firstname, String lastname, String email, RoleName roleName) {
        Specification<User> spec = Specification.where(UserSpecification.hasUsername(username))
                .and(UserSpecification.hasFirstname(firstname))
                .and(UserSpecification.hasLastname(lastname))
                .and(UserSpecification.hasEmail(email))
                .and(UserSpecification.hasRole(roleName));

        return userRepository.findAll(spec);
    }

    @Override
    public User editUser(User user, List<Long> roleIds) {
        User existing = userRepository.findById(user.getId()).orElseThrow();

        existing.setEmail(user.getEmail());
        existing.setFirstname(user.getFirstname());
        existing.setLastname(user.getLastname());
        existing.setRegisterStatus(user.getRegisterStatus());

        existing.getRoles().clear();

        if (roleIds != null) {
            for (Long roleId : roleIds) {
                Role role = roleRepository.findById(roleId).orElseThrow();
                existing.getRoles().add(role);
            }
        }

        return userRepository.save(existing);
    }

    @Override
    public List<UserResponseDto> seeAllUser() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> allUserResponseDto=new ArrayList<>();
        for(User user:users){
            UserResponseDto userResponseDto = entityToDto(user);
            allUserResponseDto.add(userResponseDto);
        }
        return allUserResponseDto;
    }


    public UserResponseDto entityToDto(User user){
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRegisterStatus()
        );

    }
}
