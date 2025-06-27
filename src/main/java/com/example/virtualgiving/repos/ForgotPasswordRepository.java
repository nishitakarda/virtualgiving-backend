package com.example.virtualgiving.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.virtualgiving.models.ForgotPassword;
import com.example.virtualgiving.models.User;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);
}
