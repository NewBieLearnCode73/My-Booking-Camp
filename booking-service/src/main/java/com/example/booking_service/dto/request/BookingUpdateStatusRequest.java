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
public class BookingUpdateStatusRequest {
    @NotEmpty(message = "Booking ID cannot be empty")
    private String bookingId;

    @Pattern(regexp = "^PENDING|UNPAID|CANCELLED|PAID", message = "Status must be one in PENDING, UNPAID, PAID, CANCELLED")
    private String status;
}
