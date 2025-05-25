package com.example.vitualgiving.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private User student;

    @ManyToOne
    @NotNull
    private Internship internship;

    private String status = "PENDING"; // PENDING, SELECTED, REJECTED

    private LocalDateTime appliedAt = LocalDateTime.now();
}
