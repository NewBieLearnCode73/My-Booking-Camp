package com.example.payment_service.service;

import com.example.payment_service.dto.request.PaymentCreateRequest;
import com.example.payment_service.dto.response.ExportRecipeResponse;
import com.example.payment_service.dto.response.PaymentResponse;
import com.example.payment_service.dto.response.TripAmountCalculatorResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    PaymentResponse createPayment(PaymentCreateRequest paymentCreateRequest, String username);
    PaymentResponse getPaymentById(String id);
    List<ExportRecipeResponse> getExportRecipeByBookingId(String bookingId, String staffUsername);
    TripAmountCalculatorResponse calculateTripAmount(String tripId);
}