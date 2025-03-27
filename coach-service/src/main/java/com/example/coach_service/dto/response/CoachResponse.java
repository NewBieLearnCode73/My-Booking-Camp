package com.example.coach_service.dto.response;

import com.example.coach_service.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachResponse {

    private String licensePlate;

    private Status status;

    private String description;

    private String coachTypeId;
}
