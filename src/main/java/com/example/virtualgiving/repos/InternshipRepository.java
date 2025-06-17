package com.example.virtualgiving.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.virtualgiving.models.Internship;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findByPostedById(Long userId);
}

