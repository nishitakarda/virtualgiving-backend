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
public class MentorshipRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    
    private Mentorship mentorship;

    private LocalDateTime registeredAt;

    private String status; // PENDING, ACCEPTED, REJECTED

    @PrePersist
    protected void onRegister() {
        this.registeredAt = LocalDateTime.now();
        this.status = "PENDING";
    }
}
