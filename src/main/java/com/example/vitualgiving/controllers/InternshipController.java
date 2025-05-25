package com.example.vitualgiving.controllers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vitualgiving.dto.InternshipRequest;
import com.example.vitualgiving.services.InternshipService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @PostMapping("/post")
    public ResponseEntity<?> postInternship(@RequestBody @Valid InternshipRequest request, Principal principal) {
        return ResponseEntity.ok(internshipService.postInternship(request, principal.getName()));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllInternships() {
        return ResponseEntity.ok(internshipService.getAllInternships());
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyInternships(Principal principal) {
        return ResponseEntity.ok(internshipService.getMyInternships(principal.getName()));
    }
}
