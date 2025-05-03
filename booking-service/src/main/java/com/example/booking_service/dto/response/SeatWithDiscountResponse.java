package com.example.booking_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatWithDiscountResponse {
    private String seatCode;
    private double discountPercent;
    private double price;
}
