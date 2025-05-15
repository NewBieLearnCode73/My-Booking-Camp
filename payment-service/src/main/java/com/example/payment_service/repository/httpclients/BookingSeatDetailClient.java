package com.example.payment_service.repository.httpclients;

import com.example.payment_service.config.FeignClientConfig;
import com.example.payment_service.dto.request.BookingStatusRequest;
import com.example.payment_service.dto.response.BookingSeatDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "booking-service-seat-details", url = "http://localhost:8099/booking", configuration = FeignClientConfig.class)
public interface BookingSeatDetailClient {
    @GetMapping(value = "/seat-details/{bookingId}",
            consumes = "application/json",
            produces = "application/json")
    List<BookingSeatDetailResponse> getAllBookingSeatDetailsByBookingId(@PathVariable String bookingId);


}
