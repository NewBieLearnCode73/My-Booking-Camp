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


}
