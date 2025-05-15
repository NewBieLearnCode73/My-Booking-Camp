package com.example.payment_service.repository.httpclients;

import com.example.payment_service.config.FeignClientConfig;
import com.example.payment_service.dto.request.BookingStatusRequest;
import com.example.payment_service.dto.response.BookingExistedResponse;
import com.example.payment_service.dto.response.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "booking-service", url = "http://localhost:8099/booking", configuration = FeignClientConfig.class)
public interface BookingClient
{
    @GetMapping(value = "/{id}",
            consumes = "application/json",
            produces = "application/json")
    BookingResponse getBookingById(@PathVariable String id);

    @GetMapping(value = "/is-existed-with-id/{id}",
            consumes = "application/json",
            produces = "application/json")
    BookingExistedResponse isBookingExisted(@PathVariable String id);

    @PutMapping(value = "/status",
            consumes = "application/json",
            produces = "application/json")
    Object updateBookingStatus(@RequestBody BookingStatusRequest bookingStatusRequest,
                               @RequestHeader("X-Auth-Username") String userUsername);
}
