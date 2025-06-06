package com.example.vitualgiving.controllers;

import com.example.vitualgiving.dto.UserProfileRequest;
import com.example.vitualgiving.models.User;
import com.example.vitualgiving.repos.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserProfileRequest updateRequest) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updateRequest.getName());
        user.setEmail(updateRequest.getEmail());
        user.setPhone(updateRequest.getPhone());
        user.setCity(updateRequest.getCity());
        user.setGithub(updateRequest.getGithub());
        user.setLinkedin(updateRequest.getLinkedin());
        user.setCourse(updateRequest.getCourse());
        user.setCollege(updateRequest.getCollege());
        user.setGraduationYear(updateRequest.getGraduationYear());
        user.setResume(updateRequest.getResume());

        userRepository.save(user);

        return ResponseEntity.ok("User profile updated successfully");
    }
}
