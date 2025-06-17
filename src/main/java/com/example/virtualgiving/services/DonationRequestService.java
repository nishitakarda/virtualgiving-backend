package com.example.virtualgiving.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.virtualgiving.dto.DonationRequestDto;
import com.example.virtualgiving.dto.DonationStatusUpdateDTO;
import com.example.virtualgiving.enums.Status;
import com.example.virtualgiving.models.DonationRequest;
import com.example.virtualgiving.models.User;
import com.example.virtualgiving.repos.DonationRequestRepository;
import com.example.virtualgiving.repos.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationRequestService {

    private final DonationRequestRepository donationRequestRepository;
    private final UserRepository userRepository;

    // Student creates a donation request
    public DonationRequest createRequest(DonationRequestDto dto, Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        DonationRequest request = new DonationRequest();
        request.setTitle(dto.getTitle());
        request.setDescription(dto.getDescription());
        request.setEmail(dto.getEmail());
        request.setPhone(dto.getPhone());
        request.setStatus(Status.PENDING);
        request.setCreatedBy(student);

        return donationRequestRepository.save(request);
    }

    // Admin views all PENDING requests
    public List<DonationRequest> getAllPendingRequests() {
        return donationRequestRepository.findByStatus(Status.PENDING);
    }

    // Admin approves or rejects a request
    public void updateRequestStatus(DonationStatusUpdateDTO dto) {
        DonationRequest request = donationRequestRepository.findById(dto.getDonationRequestId())
                .orElseThrow(() -> new RuntimeException("Donation request not found"));

        if (dto.getStatus() == Status.APPROVED || dto.getStatus() == Status.REJECTED) {
            request.setStatus(dto.getStatus());
            donationRequestRepository.save(request);
        } else {
            throw new IllegalArgumentException("Invalid status for update");
        }
    }

    // Alumni sees all APPROVED requests
    public List<DonationRequest> getAllApprovedRequests() {
        return donationRequestRepository.findByStatus(Status.APPROVED);
    }

    // Alumni gets full contact info for a specific approved request
    public DonationRequest getApprovedRequestById(Long id) {
        DonationRequest request = donationRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));

        if (request.getStatus() != Status.APPROVED) {
            throw new RuntimeException("Request is not approved");
        }

        return request;
    }
}
