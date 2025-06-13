package com.example.vitualgiving.repos;

import com.example.vitualgiving.models.MentorshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {
    List<MentorshipRequest> findByRequestedBy(String username);
}
