package com.example.virtualgiving.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.virtualgiving.dto.*;
import com.example.virtualgiving.models.Admin;
import com.example.virtualgiving.models.DonationRequest;
import com.example.virtualgiving.models.User;
import com.example.virtualgiving.security.JwtUtil;
import com.example.virtualgiving.services.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request) {
        Admin admin = adminService.findByEmail(request.getEmail());

        if (admin == null) {
            return ResponseEntity.status(401).body("Invalid admin email");
        }

        if (!adminService.checkPassword(request.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        String token = jwtUtil.generateToken(admin.getEmail());

        return ResponseEntity.ok(new AuthResponse(token, admin.getEmail(), "ADMIN"));
    }

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
