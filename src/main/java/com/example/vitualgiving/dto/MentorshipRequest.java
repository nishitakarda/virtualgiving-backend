package com.example.vitualgiving.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MentorshipRequest {

    @NotBlank
    private String topic;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Min(1)
    private Integer maxParticipants;

    @NotNull
    private LocalDateTime registrationDeadline;
}
