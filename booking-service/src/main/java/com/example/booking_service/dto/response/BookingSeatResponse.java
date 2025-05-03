package com.example.booking_service.dto.response;

import com.example.booking_service.dto.request.SeatWithDiscountRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookingSeatResponse {
    private String bookingId;
    private List<SeatWithDiscountResponse> seats;
}
