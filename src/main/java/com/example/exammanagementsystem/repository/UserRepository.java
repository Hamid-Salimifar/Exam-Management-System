package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    List<User> findByRoles_Name(RoleName roleName);


}
