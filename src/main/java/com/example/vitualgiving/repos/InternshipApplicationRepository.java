package com.example.vitualgiving.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vitualgiving.models.Internship;
import com.example.vitualgiving.models.InternshipApplication;
import com.example.vitualgiving.models.User;

import java.util.List;
import java.util.Optional;

public interface InternshipApplicationRepository extends JpaRepository<InternshipApplication, Long> {
    List<InternshipApplication> findByStudent(User student);
    List<InternshipApplication> findByInternship(Internship internship);
    Optional<InternshipApplication> findByStudentAndInternship(User student, Internship internship);
}
