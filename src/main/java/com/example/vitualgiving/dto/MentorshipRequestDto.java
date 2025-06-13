package com.example.vitualgiving.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorshipRequestDto {

    @NotBlank
    private String subject;

    @NotBlank
    private String description;
}
