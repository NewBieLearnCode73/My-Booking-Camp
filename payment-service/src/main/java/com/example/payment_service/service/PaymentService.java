package com.example.payment_service.service;

import com.example.payment_service.dto.request.PaymentCreateRequest;
import com.example.payment_service.dto.response.ExportRecipeResponse;
import com.example.payment_service.dto.response.PaymentCreateResponse;
import com.example.payment_service.dto.response.PaymentResponse;
import com.example.payment_service.dto.response.TripAmountCalculatorResponse;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    PaymentCreateResponse createPayment(PaymentCreateRequest paymentCreateRequest, String staffId);
    PaymentResponse getPaymentById(String id);
    ExportRecipeResponse getExportRecipeByBookingId(String bookingId);
    TripAmountCalculatorResponse calculateTripAmount(String tripId);
}