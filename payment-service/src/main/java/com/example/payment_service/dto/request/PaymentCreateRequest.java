package com.example.payment_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCreateRequest {
    @NotEmpty(message = "Please provide a booking id")
    private String bookingId;

    private double discountPercent;
}
