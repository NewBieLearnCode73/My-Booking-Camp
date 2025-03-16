package com.example.profile_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreationRequest {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String city;
    private String phone;
}