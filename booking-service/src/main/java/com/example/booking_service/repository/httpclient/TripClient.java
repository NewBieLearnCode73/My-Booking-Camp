package com.example.booking_service.repository.httpclient;


import com.example.booking_service.dto.response.TripExistResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trip-service", url = "http://localhost:8096/trip/is-existed-with-id")
public interface TripClient {
    @GetMapping(value = "/{id}",
            consumes = "application/json",
            produces = "application/json")
    TripExistResponse isTripExist(@PathVariable String id);
}
