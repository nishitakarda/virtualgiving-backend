package com.example.virtualgiving.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.virtualgiving.dto.DonationRequestDto;
import com.example.virtualgiving.dto.DonationStatusUpdateDTO;
import com.example.virtualgiving.models.DonationRequest;
import com.example.virtualgiving.services.DonationRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationRequestController {

    private final DonationRequestService donationRequestService;

    // Student creates a donation request
    @PostMapping("/create/{studentId}")
    public ResponseEntity<DonationRequest> createRequest(
            @PathVariable Long studentId,
            @Valid @RequestBody DonationRequestDto dto
    ) {
        DonationRequest created = donationRequestService.createRequest(dto, studentId);
        return ResponseEntity.ok(created);
    }

    // Admin fetches all pending requests
    @GetMapping("/admin/pending")
    public ResponseEntity<List<DonationRequest>> getPendingRequests() {
        return ResponseEntity.ok(donationRequestService.getAllPendingRequests());
    }

    // Admin updates status using DTO
    @PutMapping("/admin/update-status")
    public ResponseEntity<String> updateStatus(@Valid @RequestBody DonationStatusUpdateDTO dto) {
        donationRequestService.updateRequestStatus(dto);
        return ResponseEntity.ok("Status updated to " + dto.getStatus());
    }

    // Alumni views all approved donation requests
    @GetMapping("/alumni/approved")
    public ResponseEntity<List<DonationRequest>> getApprovedRequests() {
        return ResponseEntity.ok(donationRequestService.getAllApprovedRequests());
    }

    // Alumni gets contact info of a student for a particular request
    @GetMapping("/alumni/view/{id}")
    public ResponseEntity<DonationRequest> getApprovedRequestWithContact(@PathVariable Long id) {
        return ResponseEntity.ok(donationRequestService.getApprovedRequestById(id));
    }
}
