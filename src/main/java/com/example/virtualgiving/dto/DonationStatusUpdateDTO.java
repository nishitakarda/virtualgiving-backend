package com.example.virtualgiving.dto;

import com.example.virtualgiving.enums.Status;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationStatusUpdateDTO {

    private Long donationRequestId;
    private Status status;
}
