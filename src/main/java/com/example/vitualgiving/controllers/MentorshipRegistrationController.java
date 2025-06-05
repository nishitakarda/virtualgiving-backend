package com.example.vitualgiving.controllers;

import com.example.vitualgiving.models.MentorshipRegistration;
import com.example.vitualgiving.services.MentorshipRegistrationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentorship-registrations")
@RequiredArgsConstructor
public class MentorshipRegistrationController {

    private final MentorshipRegistrationService mentorshipRegistrationService;

    // 1. Request mentorship registration (student or org)
    @PostMapping("/request/{mentorshipId}")
    public ResponseEntity<?> requestMentorship(@PathVariable Long mentorshipId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        try {
            MentorshipRegistration registration = mentorshipRegistrationService
                    .requestMentorship(mentorshipId, userDetails.getUsername());
            return ResponseEntity.ok(registration);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Mentor updates registration status (ACCEPTED or REJECTED)
    @PutMapping("/{registrationId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long registrationId,
                                          @RequestParam String status,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        try {
            MentorshipRegistration updated = mentorshipRegistrationService
                    .updateRegistrationStatus(registrationId, userDetails.getUsername(), status);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // 3. Mentor views all registrations for their mentorship
    @GetMapping("/mentorship/{mentorshipId}")
    public ResponseEntity<?> getRegistrationsForMentorship(@PathVariable Long mentorshipId,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<MentorshipRegistration> registrations = mentorshipRegistrationService
                    .getRegistrationsForMentorship(mentorshipId, userDetails.getUsername());
            return ResponseEntity.ok(registrations);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // 4. User views their own mentorship registrations
    @GetMapping("/my")
    public ResponseEntity<?> getMyRegistrations(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<MentorshipRegistration> myRegs = mentorshipRegistrationService
                    .getMyRegistrations(userDetails.getUsername());
            return ResponseEntity.ok(myRegs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
