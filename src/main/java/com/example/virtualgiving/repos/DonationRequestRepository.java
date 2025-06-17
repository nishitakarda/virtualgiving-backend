package com.example.virtualgiving.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.virtualgiving.enums.Status;
import com.example.virtualgiving.models.DonationRequest;

import java.util.List;

public interface DonationRequestRepository extends JpaRepository<DonationRequest, Long> {
    List<DonationRequest> findByStatus(Status status);
}
