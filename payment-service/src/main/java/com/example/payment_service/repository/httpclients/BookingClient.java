package com.example.payment_service.repository.httpclients;

import com.example.payment_service.dto.response.BookingExistedResponse;
import com.example.payment_service.dto.response.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "booking-service", url = "http://localhost:8099/booking")
public interface BookingClient
{
    @GetMapping(value = "/get-booking-by-id/{id}",
            consumes = "application/json",
            produces = "application/json")
    BookingResponse getBookingById(@PathVariable String id);

    @GetMapping(value = "/booking/is-existed-with-id/{id}",
            consumes = "application/json",
            produces = "application/json")
    BookingExistedResponse isBookingExisted(@PathVariable String id);
}
