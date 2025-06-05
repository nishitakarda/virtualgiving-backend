package com.example.vitualgiving.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mentorship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Date and Time is required")
    private LocalDateTime dateTime;

    @NotNull(message = "Max participants is required")
    private Integer maxParticipants;

    @NotNull(message = "Registration deadline is required")
    private LocalDateTime registrationDeadline;

    @Column(nullable = true)
    private String meetingLink;

    @Column(nullable = true)
    private String meetingPassword;

    private String status; // OPEN, CLOSED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = "OPEN";
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "posted_by_id", nullable = false)
    private User postedBy;

    @ManyToMany
    @JoinTable(
        name = "mentorship_registrations",
        joinColumns = @JoinColumn(name = "mentorship_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> registeredUsers = new HashSet<>();
}
