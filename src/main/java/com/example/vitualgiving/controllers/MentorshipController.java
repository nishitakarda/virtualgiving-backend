package com.example.vitualgiving.controllers;

import com.example.vitualgiving.dto.MentorshipRequest;
import com.example.vitualgiving.models.Mentorship;
import com.example.vitualgiving.services.MentorshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/mentorships")
@RequiredArgsConstructor
public class MentorshipController {

    private final MentorshipService mentorshipService;

    @PostMapping("/post")
    public ResponseEntity<Mentorship> createMentorship(@Valid @RequestBody MentorshipRequest request,
                                                       Principal principal) {
        Mentorship mentorship = mentorshipService.createMentorship(request, principal.getName());
        return ResponseEntity.ok(mentorship);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Mentorship>> getAllMentorships() {
        return ResponseEntity.ok(mentorshipService.getAllMentorships());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Mentorship>> getMyMentorships(Principal principal) {
        return ResponseEntity.ok(mentorshipService.getMyMentorships(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mentorship> getMentorshipById(@PathVariable Long id) {
        return ResponseEntity.ok(mentorshipService.getMentorshipById(id));
    }

    @PostMapping("/{id}/meeting-details")
    public ResponseEntity<Mentorship> addMeetingDetails(@PathVariable Long id,
                                                        @RequestParam String link,
                                                        @RequestParam String password,
                                                        Principal principal) {
        Mentorship updated = mentorshipService.addMeetingDetails(id, link, password, principal.getName());
        return ResponseEntity.ok(updated);
    }
}
