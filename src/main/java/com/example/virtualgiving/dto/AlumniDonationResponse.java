package com.example.virtualgiving.dto;

import lombok.Data;

@Data
public class AlumniDonationResponse{
    private String title;
    private String description;

    // Email & phone only if approved
    private String email;
    private String phone;
}
