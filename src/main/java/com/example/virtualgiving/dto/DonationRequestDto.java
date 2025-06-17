package com.example.virtualgiving.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DonationRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;
}
