package com.example.payment_service.service.Impl;

import com.example.payment_service.dto.request.BookingStatusRequest;
import com.example.payment_service.dto.request.PaymentCreateRequest;
import com.example.payment_service.dto.response.*;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.handle.CustomRunTimeException;
import com.example.payment_service.mapper.PaymentMapper;
import com.example.payment_service.repository.PaymentRepository;
import com.example.payment_service.repository.httpclients.*;
import com.example.payment_service.service.PaymentService;
import com.example.payment_service.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private BookingClient bookingClient;

    @Autowired
    private BookingSeatDetailClient bookingSeatDetailClient;

    @Autowired
    private ProfileClient profileClient;

    @Autowired
    private TripClient tripClient;

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public PaymentResponse createPayment(PaymentCreateRequest paymentCreateRequest, String username) {

        if(!bookingClient.isBookingExisted(paymentCreateRequest.getBookingId()).isExisted()) {
            throw new CustomRunTimeException("Booking with id " + paymentCreateRequest.getBookingId() + " not found!");
        }

        Payment payment = paymentMapper.toPayment(paymentCreateRequest);
        payment.setStaffUsername(username);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentTime(LocalTime.now());

        bookingSeatDetailClient.getAllBookingSeatDetailsByBookingId(payment.getBookingId());

        List<BookingSeatDetailResponse> bookingSeatDetailResponses = bookingSeatDetailClient.getAllBookingSeatDetailsByBookingId(paymentCreateRequest.getBookingId());

        double totalAmount = 0;

        for (BookingSeatDetailResponse bookingSeatDetailResponse : bookingSeatDetailResponses) {
            totalAmount += bookingSeatDetailResponse.getPrice() * (1 - bookingSeatDetailResponse.getDiscountPercent() / 100);
        }

        payment.setTotalAmount(totalAmount);
        payment.setBookingId(paymentCreateRequest.getBookingId());

        BookingStatusRequest bookingStatusRequest = new BookingStatusRequest();
        bookingStatusRequest.setBookingId(paymentCreateRequest.getBookingId());
        bookingStatusRequest.setStatus(Status.PAID);


        Object updateBookingStatusResponse = bookingClient.updateBookingStatus(bookingStatusRequest, username);
        if (updateBookingStatusResponse == null) {
            throw new CustomRunTimeException("Failed to update booking status");
        }

        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponse getPaymentById(String id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new CustomRunTimeException("Payment with id " + id + " not found!"));
        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    public ExportRecipeResponse getExportRecipeByBookingId(String bookingId) {
        return null;
    }

    @Override
    public TripAmountCalculatorResponse calculateTripAmount(String tripId) {

    }
}
