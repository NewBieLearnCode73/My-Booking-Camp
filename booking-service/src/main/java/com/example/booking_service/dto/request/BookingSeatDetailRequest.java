package com.example.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingSeatDetailRequest {
    private String id;

    private String bookingId;

    private String seatCode;

    private double discountPercent = 0.0;

    private double price;
}
