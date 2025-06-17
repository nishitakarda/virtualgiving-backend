package com.example.virtualgiving.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String role;
}
