package com.example.payment_service.service.Impl;

import com.example.payment_service.dto.request.PaymentCreateRequest;
import com.example.payment_service.dto.response.*;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.handle.CustomRunTimeException;
import com.example.payment_service.mapper.PaymentMapper;
import com.example.payment_service.repository.PaymentRepository;
import com.example.payment_service.repository.httpclients.AuthClient;
import com.example.payment_service.repository.httpclients.BookingClient;
import com.example.payment_service.repository.httpclients.ProfileClient;
import com.example.payment_service.repository.httpclients.TripClient;
import com.example.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private BookingClient bookingClient;

    @Autowired
    private ProfileClient profileClient;

    @Autowired
    private TripClient tripClient;

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public PaymentCreateResponse createPayment(PaymentCreateRequest paymentCreateRequest, String staffId) {
       return null;


    }

    @Override
    public PaymentResponse getPaymentById(String id) {
        return null;
    }

    @Override
    public ExportRecipeResponse getExportRecipeByBookingId(String bookingId) {
        return null;
    }

    @Override
    public TripAmountCalculatorResponse calculateTripAmount(String tripId) {
        return null;
    }
}
