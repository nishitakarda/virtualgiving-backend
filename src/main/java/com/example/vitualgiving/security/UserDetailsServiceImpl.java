package com.example.vitualgiving.security;

import java.util.Collections;

import com.example.vitualgiving.models.User;
import com.example.vitualgiving.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

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

