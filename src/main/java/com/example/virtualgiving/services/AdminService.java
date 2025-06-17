package com.example.virtualgiving.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.virtualgiving.dto.AdminUserVerificationRequest;
import com.example.virtualgiving.dto.DonationStatusUpdateDTO;
import com.example.virtualgiving.enums.Status;
import com.example.virtualgiving.models.Admin;
import com.example.virtualgiving.models.DonationRequest;
import com.example.virtualgiving.models.User;
import com.example.virtualgiving.repos.AdminRepository;
import com.example.virtualgiving.repos.DonationRequestRepository;
import com.example.virtualgiving.repos.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final DonationRequestRepository donationRequestRepository;
    private final PasswordEncoder passwordEncoder;

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElse(null);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<User> getPendingUsers() {
        return userRepository.findByStatus(Status.PENDING);
    }

    public void updateUserStatus(AdminUserVerificationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(request.getStatus());
        userRepository.save(user);
    }

    public List<DonationRequest> getPendingDonations() {
        return donationRequestRepository.findByStatus(Status.PENDING);
    }

    public void updateDonationStatus(DonationStatusUpdateDTO request) {
        DonationRequest donation = donationRequestRepository.findById(request.getDonationRequestId())
                .orElseThrow(() -> new RuntimeException("Donation not found"));
        donation.setStatus(request.getStatus());
        donationRequestRepository.save(donation);
    }
}
