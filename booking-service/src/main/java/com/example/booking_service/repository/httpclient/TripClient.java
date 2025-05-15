package com.example.booking_service.repository.httpclient;


import com.example.booking_service.config.FeignClientConfig;
import com.example.booking_service.dto.response.TripExistResponse;
import com.example.booking_service.dto.response.TripResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trip-service", url = "http://localhost:8096/trip", configuration = FeignClientConfig.class)
public interface TripClient {
    @GetMapping(value = "/is-existed-with-id/{id}",
            consumes = "application/json",
            produces = "application/json")
    TripExistResponse isTripExist(@PathVariable String id);

    @GetMapping(value = "/{id}",
            consumes = "application/json",
            produces = "application/json")
    TripResponse getTripById(@PathVariable String id);
}
