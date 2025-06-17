package com.example.virtualgiving.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.virtualgiving.models.Mentorship;

@Repository
public interface MentorshipRepository extends JpaRepository<Mentorship, Long> {
    List<Mentorship> findByPostedById(Long userId);
}
