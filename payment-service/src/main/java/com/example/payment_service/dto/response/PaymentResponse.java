package com.example.payment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String id;
    private String bookingId;
    private String staffUsername;
    private double totalAmount;
    private String paymentDate;
    private String paymentTime;
}
