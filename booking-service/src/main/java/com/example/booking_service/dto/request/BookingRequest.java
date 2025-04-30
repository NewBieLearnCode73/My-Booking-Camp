package com.example.booking_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotEmpty(message = "Trip ID cannot be empty")
    private String tripId;
    @NotEmpty(message = "Please provide booked seats")
    private List<String> bookedSeats;
    private String note;
    private String phoneNumber;
}
