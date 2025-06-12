package com.example.vitualgiving.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Company Name is required")
    private String companyName;

    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Salary is required")
    private String salary;

    @NotBlank(message = "Duration is required")
    private String durationMonths;

    @ManyToOne
    @JoinColumn(name = "posted_by_id", nullable = false)
    private User postedBy;

    @Column(updatable = false)
    private final LocalDate postedAt = LocalDate.now();
}