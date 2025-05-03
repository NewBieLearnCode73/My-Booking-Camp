package com.example.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookingSeatDiscountRequest {
    private String bookingId;
    private List<SeatWithDiscountRequest> seats;
}
