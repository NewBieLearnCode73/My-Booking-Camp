package com.example.payment_service.repository.httpclients;

import com.example.payment_service.dto.response.TripResponse;
import com.example.payment_service.service.Impl.TripExistResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trip-service", url = "http://localhost:8096/trip")
public interface TripClient {
    @GetMapping(value = "/{id}",
            consumes = "application/json",
            produces = "application/json")
    TripResponse getTripById(@PathVariable String id);

    @GetMapping(value = "/is-existed-with-id/{id}",
            consumes = "application/json",
            produces = "application/json")
    TripExistResponse isTripExisted(@PathVariable String id);
}