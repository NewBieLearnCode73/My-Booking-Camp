package com.example.coach_service.dto.request;

import com.example.coach_service.utils.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachRequest {

    @NotEmpty(message = "Please provide a license plate")
    private String licensePlate;

    @Pattern(regexp = "^ACTIVE|INACTIVE|MAINTENANCE$", message = "Status must be one in ACTIVE, INACTIVE or MAINTENANCE")
    private String status;

    @NotEmpty(message = "Please provide a description")
    private String description;

    @NotEmpty(message = "Please provide a coach type id")
    private String coachTypeId;
}
