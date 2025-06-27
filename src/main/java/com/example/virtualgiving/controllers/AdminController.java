package com.example.virtualgiving.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.virtualgiving.dto.AdminUserVerificationRequest;
import com.example.virtualgiving.dto.DonationStatusUpdateDTO;
import com.example.virtualgiving.models.DonationRequest;
import com.example.virtualgiving.models.User;
import com.example.virtualgiving.services.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/pending-users")
    public ResponseEntity<List<User>> getPendingUsers() {
        return ResponseEntity.ok(adminService.getPendingUsers());
    }

    @PostMapping("/update-user-status")
    public ResponseEntity<String> updateUserStatus(@RequestBody AdminUserVerificationRequest request) {
        adminService.updateUserStatus(request);
        return ResponseEntity.ok("User status updated successfully");
    }

    @GetMapping("/pending-donations")
    public ResponseEntity<List<DonationRequest>> getPendingDonations() {
        return ResponseEntity.ok(adminService.getPendingDonations());
    }

    @PostMapping("/update-donation-status")
    public ResponseEntity<String> updateDonationStatus(@RequestBody DonationStatusUpdateDTO request) {
        adminService.updateDonationStatus(request);
        return ResponseEntity.ok("Donation status updated successfully");
    }
}
