package com.example.vitualgiving.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MentorshipResponse {
    private Long mentorshipId;
    private String topic;
    private String description;
    private LocalDateTime dateTime;
    private String status;
    private String meetingLink;
    private String meetingPassword;
}
