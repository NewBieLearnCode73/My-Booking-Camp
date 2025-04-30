package com.example.booking_service.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingTripAndStatusRequest {
    @NotEmpty(message = "Trip ID cannot be empty")
    private String tripId;

    @Pattern(regexp = "^PENDING|UNPAID|PAID|CANCELLED$", message = "Status must be one in PENDING, UNPAID, PAID, CANCELLED")
    private String status;
}
