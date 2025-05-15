package com.example.booking_service.controller;

import com.example.booking_service.custom.RequireRole;
import com.example.booking_service.service.BookingSeatDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingSeatDetailController {
    @Autowired
    private BookingSeatDetailService bookingSeatDetailService;

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF", "ROLE_CUSTOMER"})
    @GetMapping("/booking/seat-details/{bookingId}")
    ResponseEntity<?> getAllBookingSeatDetailsByBookingId(@PathVariable String bookingId) {
        return ResponseEntity.ok(bookingSeatDetailService.getAllBookingSeatDetailsByBookingId(bookingId));
    }
}
