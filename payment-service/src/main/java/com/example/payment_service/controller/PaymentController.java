package com.example.payment_service.controller;

import com.example.payment_service.dto.request.PaymentCreateRequest;
import com.example.payment_service.dto.response.PaymentResponse;
import com.example.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/create")
    ResponseEntity<?> createPayment(@RequestBody PaymentCreateRequest paymentCreateRequest, @RequestHeader("X-Auth-Username") String username) {
        return ResponseEntity.ok(paymentService.createPayment(paymentCreateRequest, username));
    }

    @GetMapping("/payment/{id}")
    ResponseEntity<?> getPaymentById(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/payment/export-recipe/{bookingId}")
    ResponseEntity<?> getExportRecipeByBookingId(@PathVariable String bookingId, @RequestHeader("X-Auth-Username") String staffUsername) {
        return ResponseEntity.ok(paymentService.getExportRecipeByBookingId(bookingId, staffUsername));
    }

    @GetMapping("/payment/calculate-trip-amount/{tripId}")
    ResponseEntity<?> calculateTripAmount(@PathVariable String tripId) {
        return ResponseEntity.ok(paymentService.calculateTripAmount(tripId));
    }

}
