package com.example.booking_service.controller;

import com.example.booking_service.dto.request.*;
import com.example.booking_service.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/booking")
    public ResponseEntity<?> getAllBookings(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.getBookings(pageNo, pageSize, sortBy));
    }

    @GetMapping("/booking/tripId/{id}")
    public ResponseEntity<?> getBookingsByTripId(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.getBookingsByTripId(id, pageNo, pageSize, sortBy));
    }

    @GetMapping("/booking/userUsername/{username}")
    public ResponseEntity<?> getBookingByUserUsername(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.getBookingByUserUsername(username, pageNo, pageSize, sortBy));
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.getBookingById(id));
    }


    @PostMapping("/booking")
    public ResponseEntity<?> createBooking(@RequestBody @Valid BookingRequest bookingRequest,
                                           @RequestHeader("X-Auth-Username") String userUsername,
                                           @RequestHeader("X-Auth-Roles") String userRole
                                           ) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(bookingService.createBooking(bookingRequest, userUsername, userRole));
    }

    @PutMapping("/booking/tripId/status")
    public ResponseEntity<?> getBookingsByTripIdWithBookingStatus(
            @RequestBody @Valid BookingTripAndStatusRequest bookingTripAndStatusRequest,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.getBookingsByTripIdWithBookingStatus(bookingTripAndStatusRequest, pageNo, pageSize, sortBy));
    }

    @PutMapping("/booking/status")
    public ResponseEntity<?> updateBookingStatus(
            @RequestBody @Valid BookingUpdateStatusRequest bookingUpdateStatusRequest,
            @RequestHeader("X-Auth-Username") String userUsername
    ){
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.updateBookingStatus(bookingUpdateStatusRequest, userUsername));
    }

    @PutMapping("/booking/seats")
    public ResponseEntity<?> updateBookingSeats(
            @RequestBody @Valid BookingSeatsUpdateRequest bookingSeatsUpdateRequest
    ){
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.updateBookingSeats(bookingSeatsUpdateRequest));
    }

    @GetMapping("/booking/is-existed-with-id/{id}")
    public ResponseEntity<?> isBookingExist(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.isBookingExist(id));
    }

    @GetMapping("/booking/get-all-seats/{bookingId}")
    public ResponseEntity<?> getAllSeats(@PathVariable String bookingId) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.getAllSeats(bookingId));
    }

    @PutMapping("/booking/add-discount")
    public ResponseEntity<?> addDiscount(@RequestBody BookingSeatDiscountRequest bookingSeatDiscountRequest) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(bookingService.addDiscount(bookingSeatDiscountRequest));
    }
}