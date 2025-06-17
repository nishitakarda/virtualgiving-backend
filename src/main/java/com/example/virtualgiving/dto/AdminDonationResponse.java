package com.example.virtualgiving.dto;

import lombok.Data;
import java.time.LocalDate;

import com.example.virtualgiving.enums.Status;

@Data
public class AdminDonationResponse {
    private Long id;
    private String title;
    private String description;
    private String email;
    private String phone;
    private Status status;
    private LocalDate createdAt;
}
