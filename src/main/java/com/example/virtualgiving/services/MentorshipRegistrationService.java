package com.example.virtualgiving.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.virtualgiving.dto.MentorshipResponse;
import com.example.virtualgiving.models.Mentorship;
import com.example.virtualgiving.models.MentorshipRegistration;
import com.example.virtualgiving.models.User;
import com.example.virtualgiving.repos.MentorshipRegistrationRepository;
import com.example.virtualgiving.repos.MentorshipRepository;
import com.example.virtualgiving.repos.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorshipRegistrationService {

    private final MentorshipRegistrationRepository mentorshipRegistrationRepository;
    private final MentorshipRepository mentorshipRepository;
    private final UserRepository userRepository;

    // Request mentorship registration (student or org)
    public MentorshipRegistration requestMentorship(Long mentorshipId, String email) {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Check if user role is allowed to request mentorship (student or organization)
    if (!(user.getRole().equalsIgnoreCase("STUDENT") || user.getRole().equalsIgnoreCase("ORGANIZATION"))) {
        throw new IllegalStateException("Only students or organizations can request mentorship.");
    }

    Mentorship mentorship = mentorshipRepository.findById(mentorshipId)
            .orElseThrow(() -> new RuntimeException("Mentorship not found"));

    mentorshipRegistrationRepository.findByUserAndMentorship(user, mentorship)
            .ifPresent(reg -> {
                throw new IllegalStateException("You have already requested mentorship registration.");
            });

    MentorshipRegistration registration = MentorshipRegistration.builder()
            .user(user)
            .mentorship(mentorship)
            .status("PENDING")
            .build();

    return mentorshipRegistrationRepository.save(registration);
}


    // Mentor updates registration status (ACCEPTED or REJECTED)
    public MentorshipRegistration updateRegistrationStatus(Long registrationId, String email, String status) {
        MentorshipRegistration registration = mentorshipRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Mentorship mentorship = registration.getMentorship();

        if (!mentorship.getPostedBy().getId().equals(user.getId())) {
            throw new AccessDeniedException("Only the mentorship owner can update registration status.");
        }

        if (!status.equalsIgnoreCase("ACCEPTED") && !status.equalsIgnoreCase("REJECTED")) {
            throw new IllegalArgumentException("Status must be ACCEPTED or REJECTED");
        }

        registration.setStatus(status.toUpperCase());

        return mentorshipRegistrationRepository.save(registration);
    }

    // Mentor views all registrations for their mentorship
    public List<MentorshipRegistration> getRegistrationsForMentorship(Long mentorshipId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Mentorship mentorship = mentorshipRepository.findById(mentorshipId)
                .orElseThrow(() -> new RuntimeException("Mentorship not found"));

        if (!mentorship.getPostedBy().getId().equals(user.getId())) {
            throw new AccessDeniedException("Only the mentorship owner can view registrations.");
        }

        return mentorshipRegistrationRepository.findByMentorship(mentorship);
    }

    // User views their own registrations
    public List<MentorshipRegistration> getMyRegistrations(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return mentorshipRegistrationRepository.findByUser(user);
    }

    // Return all accepted mentorships for a user with meeting link and password
    public List<MentorshipResponse> getAcceptedMentorshipsWithMeetingDetails(String email) {
         User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<MentorshipRegistration> acceptedRegs = mentorshipRegistrationRepository
                .findByUserAndStatus(user, "ACCEPTED");

        return acceptedRegs.stream()
                .map(reg -> {
                    Mentorship mentorship = reg.getMentorship();
                    return MentorshipResponse.builder()
                        .mentorshipId(mentorship.getId())
                        .topic(mentorship.getTopic())
                        .description(mentorship.getDescription())
                        .dateTime(mentorship.getDateTime())
                        .status(reg.getStatus())
                        .meetingLink(mentorship.getMeetingLink())
                        .meetingPassword(mentorship.getMeetingPassword())
                        .build();
            }).toList();
}

}
