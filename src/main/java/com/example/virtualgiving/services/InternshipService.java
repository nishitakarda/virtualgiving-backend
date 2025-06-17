package com.example.virtualgiving.services;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.virtualgiving.dto.InternshipRequest;
import com.example.virtualgiving.models.Internship;
import com.example.virtualgiving.models.User;
import com.example.virtualgiving.repos.InternshipRepository;
import com.example.virtualgiving.repos.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;

    public Internship postInternship(InternshipRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getRole().equals("ORGANIZATION") && !user.getRole().equals("ALUMNI")) {
            throw new AccessDeniedException("Only organizations or alumni can post internships.");
        }

        Internship internship = new Internship();
        internship.setTitle(request.getTitle());
        internship.setCompanyName(request.getCompanyName());
        internship.setDescription(request.getDescription());
        internship.setLocation(request.getLocation());
        internship.setSalary(request.getSalary());
        internship.setDurationMonths(request.getDurationMonths());
        internship.setPostedBy(user);

        return internshipRepository.save(internship);
    }

    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    public List<Internship> getMyInternships(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return internshipRepository.findByPostedById(user.getId());
    }
}
