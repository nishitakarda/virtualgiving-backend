package com.example.virtualgiving.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.virtualgiving.dto.MentorshipRequestDto;
import com.example.virtualgiving.models.MentorshipRequest;
import com.example.virtualgiving.models.User;
import com.example.virtualgiving.repos.MentorshipRequestRepository;
import com.example.virtualgiving.repos.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorshipRequestService {

    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final UserRepository userRepository;

    public MentorshipRequest createRequest(MentorshipRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // ✅ Only STUDENT or ORGANIZATION allowed
        if (!user.getRole().equalsIgnoreCase("STUDENT") && !user.getRole().equalsIgnoreCase("ORGANIZATION")) {
            throw new AccessDeniedException("Only students or organizations can create mentorship requests.");
        }

        MentorshipRequest request = MentorshipRequest.builder()
                .subject(requestDto.getSubject())
                .description(requestDto.getDescription())
                .requestedBy(email)
                .requestedAt(LocalDateTime.now())
                .build();

        return mentorshipRequestRepository.save(request);
    }

    public List<MentorshipRequest> getAllRequests() {
        return mentorshipRequestRepository.findAll();
    }

    public List<MentorshipRequest> getMyRequests(String email) {
        return mentorshipRequestRepository.findByRequestedBy(email);
    }

    public MentorshipRequest getById(Long id) {
        return mentorshipRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentorship request not found with ID: " + id));
    }
}
