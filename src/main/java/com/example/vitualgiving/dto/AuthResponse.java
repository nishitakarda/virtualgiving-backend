package com.example.vitualgiving.dto;

import com.example.vitualgiving.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private Role role;
}
