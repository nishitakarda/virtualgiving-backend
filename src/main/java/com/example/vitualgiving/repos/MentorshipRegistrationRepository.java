package com.example.vitualgiving.repos;

import com.example.vitualgiving.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface MentorshipRegistrationRepository extends JpaRepository<MentorshipRegistration, Long> {
    List<MentorshipRegistration> findByUser(User user);
    List<MentorshipRegistration> findByMentorship(Mentorship mentorship);
    Optional<MentorshipRegistration> findByUserAndMentorship(User user, Mentorship mentorship);
}
