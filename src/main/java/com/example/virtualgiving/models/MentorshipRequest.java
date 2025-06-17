package com.example.virtualgiving.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Subject is required")
    private String subject;

    @Column(length = 1000)
    private String description;

    private String requestedBy; // student/org username or email

    private LocalDateTime requestedAt;
}
