package com.example.payment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExportRecipeResponse {
    private String id;
    private String bookingId;
    private String routeName;
    private String staffUsername;
    private String phoneNumber;
    private String seatNumber;
    private double discountPercent;
    private double totalAmount;
    private String exportDate;
    private String exportTime;
}