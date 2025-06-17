package com.example.virtualgiving.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.virtualgiving.models.MentorshipRequest;

import java.util.List;

public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {
    List<MentorshipRequest> findByRequestedBy(String username);
}
