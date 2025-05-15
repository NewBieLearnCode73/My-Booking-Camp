package com.example.trip_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest {

    @NotEmpty(message = "Route ID is required")
    private String routeId;

    @NotEmpty(message = "Coach ID is required")
    private String coachId;

    @NotEmpty(message = "Driver ID is required")
    private String driverId;

    @NotEmpty(message = "Company ID is required")
    private String companyId;

    @NotEmpty(message = "Departure date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Departure date should be in the format yyyy-MM-dd")
    private String departureDate; // "2025-03-30"

    @NotEmpty(message = "Departure time is required")
    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$", message = "Departure time should be in the format HH:mm:ss")
    private String departureTime; //  "10:00:00"

    @NotEmpty(message = "Arrival date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Departure date should be in the format yyyy-MM-dd")
    private String arrivalDate;   //  "2025-03-30"

    @NotEmpty(message = "Arrival time is required")
    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$", message = "Arrival time should be in the format HH:mm:ss")
    private String arrivalTime;   //  "12:00:00"

    @NotEmpty(message = "Status is required")
    @Pattern(regexp = "^(SCHEDULED|IN_PROGRESS|COMPLETED|CANCELLED|PENDING)$", message = "Status should be either SCHEDULED, IN_PROGRESS, COMPLETED or CANCELLED")
    private String status;

    @NotNull(message = "Base price is required")
    private Double basePrice;
}