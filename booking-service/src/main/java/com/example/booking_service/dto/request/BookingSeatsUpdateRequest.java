package com.example.booking_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingSeatsUpdateRequest {
    @NotEmpty(message = "Trip ID cannot be empty")
    private String bookingId;

    @NotEmpty(message = "Please provide booked seats")
    private List<String> bookedSeats;
}
