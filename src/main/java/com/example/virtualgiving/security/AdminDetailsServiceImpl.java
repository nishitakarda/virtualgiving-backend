package com.example.virtualgiving.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.virtualgiving.models.Admin;
import com.example.virtualgiving.repos.AdminRepository;

import java.util.Collections;

@Service("adminDetailsService")
@RequiredArgsConstructor
public class AdminDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        return new org.springframework.security.core.userdetails.User(
                admin.getEmail(),
                admin.getPassword(),
                Collections.singleton(() -> "ROLE_ADMIN")
        );
    }
}
