package com.example.virtualgiving.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.virtualgiving.models.*;

import java.util.*;

public interface MentorshipRegistrationRepository extends JpaRepository<MentorshipRegistration, Long> {
    List<MentorshipRegistration> findByUser(User user);
    List<MentorshipRegistration> findByMentorship(Mentorship mentorship);
    Optional<MentorshipRegistration> findByUserAndMentorship(User user, Mentorship mentorship);
    List<MentorshipRegistration> findByUserAndStatus(User user, String status);

}
