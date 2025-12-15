package com.example.exammanagementsystem.security;

import com.example.exammanagementsystem.model.Role;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User userFromDB =userService.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username not found!"));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<Role> roles = userFromDB.getRoles();
        for(Role role:roles){
            String stringRole = role.getName().toString();
            grantedAuthorities.add(new SimpleGrantedAuthority(stringRole));
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(userFromDB.getUsername())
                .password(userFromDB.getPassword())
                .authorities(grantedAuthorities)
                .build();

    }
}
