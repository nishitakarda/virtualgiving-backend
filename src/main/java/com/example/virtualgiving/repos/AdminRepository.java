package com.example.virtualgiving.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.virtualgiving.models.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email); 
}
