package com.example.vitualgiving.controllers;

import com.example.vitualgiving.dto.MentorshipRequestDto;
import com.example.vitualgiving.models.MentorshipRequest;
import com.example.vitualgiving.services.MentorshipRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/mentorship-requests")
@RequiredArgsConstructor
public class MentorshipRequestController {

    private final MentorshipRequestService mentorshipRequestService;

    @PostMapping("/create")
    public ResponseEntity<MentorshipRequest> createRequest(@Valid @RequestBody MentorshipRequestDto dto,
                                                           Principal principal) {
        MentorshipRequest request = mentorshipRequestService.createRequest(dto, principal.getName());
        return ResponseEntity.ok(request);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MentorshipRequest>> getAllRequests() {
        return ResponseEntity.ok(mentorshipRequestService.getAllRequests());
    }

    @GetMapping("/my")
    public ResponseEntity<List<MentorshipRequest>> getMyRequests(Principal principal) {
        return ResponseEntity.ok(mentorshipRequestService.getMyRequests(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorshipRequest> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mentorshipRequestService.getById(id));
    }
}
