package com.example.virtualgiving.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.virtualgiving.dto.MentorshipRequest;
import com.example.virtualgiving.models.Mentorship;
import com.example.virtualgiving.models.User;
import com.example.virtualgiving.repos.MentorshipRepository;
import com.example.virtualgiving.repos.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorshipService {

    private final MentorshipRepository mentorshipRepository;
    private final UserRepository userRepository;

    public Mentorship createMentorship(MentorshipRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getRole().equals("ALUMNI")) {
            throw new AccessDeniedException("Only alumni can post mentorships.");
        }

        Mentorship mentorship = Mentorship.builder()
                .topic(request.getTopic())
                .description(request.getDescription())
                .dateTime(request.getDateTime())
                .maxParticipants(request.getMaxParticipants())
                .registrationDeadline(request.getRegistrationDeadline())
                .postedBy(user)
                .status("OPEN")
                .build();

        return mentorshipRepository.save(mentorship);
    }

    public List<Mentorship> getAllMentorships() {
        return mentorshipRepository.findAll();
    }

    public List<Mentorship> getMyMentorships(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mentorshipRepository.findByPostedById(user.getId());
    }

    public Mentorship getMentorshipById(Long id) {
        return mentorshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentorship not found"));
    }

    public Mentorship addMeetingDetails(Long mentorshipId, String link, String password, String email) {
        Mentorship mentorship = mentorshipRepository.findById(mentorshipId)
                .orElseThrow(() -> new RuntimeException("Mentorship not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Only owner (postedBy) can add meeting details
        if (!mentorship.getPostedBy().getId().equals(user.getId())) {
            throw new AccessDeniedException("Only the mentorship owner can add meeting details.");
        }

        mentorship.setMeetingLink(link);
        mentorship.setMeetingPassword(password);

        return mentorshipRepository.save(mentorship);
    }
}
