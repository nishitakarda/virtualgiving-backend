package com.example.virtualgiving.dto;

import com.example.virtualgiving.enums.Status;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserVerificationRequest {
    private Long userId;
    private Status status; // Should be either APPROVED or REJECTED
}
