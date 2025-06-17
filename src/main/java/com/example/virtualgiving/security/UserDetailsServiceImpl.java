package com.example.virtualgiving.security;

import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.virtualgiving.models.User;
import com.example.virtualgiving.repos.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),                    // username
                user.getPassword(),                // password
                Collections.singleton(() -> "ROLE_" + user.getRole()) // authorities
        );
    }
}

