package com.example.vitualgiving.controllers;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.vitualgiving.models.Internship;
import com.example.vitualgiving.models.InternshipApplication;
import com.example.vitualgiving.models.User;
import com.example.vitualgiving.repos.InternshipApplicationRepository;
import com.example.vitualgiving.repos.InternshipRepository;
import com.example.vitualgiving.repos.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class InternshipApplicationController {

    private final InternshipApplicationRepository applicationRepo;
    private final InternshipRepository internshipRepo;
    private final UserRepository userRepo;

    // 1. Apply to Internship (Student only)
    @PostMapping("/apply/{internshipId}")
    public ResponseEntity<?> applyToInternship(@PathVariable Long internshipId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        User student = userRepo.findByEmail(userDetails.getUsername()).orElse(null);
        if (student == null || !student.getRole().equalsIgnoreCase("STUDENT")) {
            return ResponseEntity.status(403).body("Only students can apply.");
        }

        Optional<Internship> internshipOpt = internshipRepo.findById(internshipId);
        if (internshipOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Internship not found");
        }

        if (applicationRepo.findByStudentAndInternship(student, internshipOpt.get()).isPresent()) {
            return ResponseEntity.badRequest().body("Already applied to this internship.");
        }

        InternshipApplication app = InternshipApplication.builder()
                .student(student)
                .internship(internshipOpt.get())
                .status("PENDING")
                .build();

        applicationRepo.save(app);
        return ResponseEntity.ok("Applied successfully.");
    }

    // 2. View applications by student
    @GetMapping("/my")
    public ResponseEntity<?> myApplications(@AuthenticationPrincipal UserDetails userDetails) {
        User student = userRepo.findByEmail(userDetails.getUsername()).orElse(null);
        if (student == null || !student.getRole().equalsIgnoreCase("STUDENT")) {
            return ResponseEntity.status(403).body("Only students can view their applications.");
        }

        return ResponseEntity.ok(applicationRepo.findByStudent(student));
    }

    // 3. Org/Alumni view applicants for their internship
    @GetMapping("/internship/{id}")
    public ResponseEntity<?> getApplicants(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null || !(user.getRole().equalsIgnoreCase("ORGANIZATION") || user.getRole().equalsIgnoreCase("ALUMNI"))) {
            return ResponseEntity.status(403).body("Only Organization or Alumni can view applicants.");
        }

        Optional<Internship> internship = internshipRepo.findById(id);
        if (internship.isEmpty() || !internship.get().getPostedBy().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body("Unauthorized or Internship not found.");
        }

        return ResponseEntity.ok(applicationRepo.findByInternship(internship.get()));
    }

    // 4. Select or Reject applicant
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long applicationId,
                                          @RequestParam String status,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null || !(user.getRole().equalsIgnoreCase("ORGANIZATION") || user.getRole().equalsIgnoreCase("ALUMNI"))) {
            return ResponseEntity.status(403).body("Only Organization or Alumni can update status.");
        }

        Optional<InternshipApplication> appOpt = applicationRepo.findById(applicationId);
        if (appOpt.isEmpty()) return ResponseEntity.badRequest().body("Application not found");

        InternshipApplication app = appOpt.get();
        if (!app.getInternship().getPostedBy().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body("You did not post this internship.");
        }

        if (!status.equalsIgnoreCase("SELECTED") && !status.equalsIgnoreCase("REJECTED")) {
            return ResponseEntity.badRequest().body("Status must be SELECTED or REJECTED");
        }

        app.setStatus(status.toUpperCase());
        applicationRepo.save(app);

        return ResponseEntity.ok("Status updated to " + status.toUpperCase());
    }
}
