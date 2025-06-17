package com.example.virtualgiving.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.virtualgiving.enums.Status;
import com.example.virtualgiving.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByStatus(Status pending);
}
