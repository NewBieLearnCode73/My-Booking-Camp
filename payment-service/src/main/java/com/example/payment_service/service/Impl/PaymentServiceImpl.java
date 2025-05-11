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
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingClient bookingClient;

    @Autowired
    private BookingSeatDetailClient bookingSeatDetailClient;

    @Autowired
    private TripClient tripClient;

    @Autowired
    private RouteClient routeClient;

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
    public List<ExportRecipeResponse> getExportRecipeByBookingId(String bookingId, String staffUsername) {
        if (!bookingClient.isBookingExisted(bookingId).isExisted()) {
            throw new CustomRunTimeException("Booking with id " + bookingId + " not found!");
        }

        BookingResponse bookingResponse = bookingClient.getBookingById(bookingId);
        if (bookingResponse == null) {
            throw new CustomRunTimeException("Failed to retrieve booking details");
        }

        TripResponse tripResponse = tripClient.getTripById(bookingResponse.getTripId());
        if (tripResponse == null) {
            throw new CustomRunTimeException("Trip not found for booking " + bookingId);
        }

        RouteResponse routeResponse = routeClient.getRouteById(tripResponse.getRouteId());
        if (routeResponse == null) {
            throw new CustomRunTimeException("Route not found for trip " + tripResponse.getId());
        }

        List<BookingSeatDetailResponse> seatDetails = bookingSeatDetailClient.getAllBookingSeatDetailsByBookingId(bookingId);
        if (seatDetails == null || seatDetails.isEmpty()) {
            throw new CustomRunTimeException("No seat details found for booking " + bookingId);
        }

        String routeName = routeResponse.getStartLocation() + " - " + routeResponse.getEndLocation();
        String currentDate = LocalDate.now().toString();
        String currentTime = LocalTime.now().toString();

        List<ExportRecipeResponse> exportRecipeResponses = new ArrayList<>();
        for (BookingSeatDetailResponse seat : seatDetails) {
            double discountedAmount = calculateDiscountedAmount(seat.getPrice(), seat.getDiscountPercent());

            ExportRecipeResponse exportRecipe = ExportRecipeResponse.builder()
                    .id(seat.getId())
                    .bookingId(bookingId)
                    .routeName(routeName)
                    .staffUsername(staffUsername)
                    .phoneNumber(bookingResponse.getPhoneNumber())
                    .seatNumber(seat.getSeatCode())
                    .discountPercent(seat.getDiscountPercent())
                    .totalAmount(discountedAmount)
                    .exportDate(currentDate)
                    .exportTime(currentTime)
                    .build();

            exportRecipeResponses.add(exportRecipe);
        }

        return exportRecipeResponses;
    }

    private double calculateDiscountedAmount(double price, double discountPercent) {
        return price * (1 - discountPercent / 100);
    }

    @Override
    public TripAmountCalculatorResponse calculateTripAmount(String tripId) {
        if (!tripClient.isTripExisted(tripId).isExisted()) {
            throw new CustomRunTimeException("Trip with id " + tripId + " not found!");
        }

        TripResponse tripResponse = tripClient.getTripById(tripId);
        if (tripResponse == null) {
            throw new CustomRunTimeException("Failed to retrieve trip details");
        }

        RouteResponse routeResponse = routeClient.getRouteById(tripResponse.getRouteId());
        if (routeResponse == null) {
            throw new CustomRunTimeException("Route not found for trip " + tripId);
        }

        double totalAmount = 0;
        List<BookingSeatDetailResponse> seatDetails = bookingSeatDetailClient.getAllBookingSeatDetailsByBookingId(tripId);
        if (seatDetails != null && !seatDetails.isEmpty()) {
            for (BookingSeatDetailResponse seat : seatDetails) {
                totalAmount += calculateDiscountedAmount(seat.getPrice(), seat.getDiscountPercent());
            }
        }

        return TripAmountCalculatorResponse.builder()
                .tripId(tripId)
                .totalAmount(totalAmount)
                .build();
    }
}
