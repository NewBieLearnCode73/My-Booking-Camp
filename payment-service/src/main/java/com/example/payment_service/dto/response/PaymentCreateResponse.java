package com.example.payment_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCreateResponse {
    private String id;
    private String bookingId;
    private String tripId;
    private double discountPercent;
    private double totalAmount;
    private String status;
    private String paymentMethod;
    private String createdAt;
    private String updatedAt;
}
