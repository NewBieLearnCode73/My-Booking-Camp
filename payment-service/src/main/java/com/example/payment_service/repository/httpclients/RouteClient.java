package com.example.payment_service.repository.httpclients;

import com.example.payment_service.config.FeignClientConfig;
import com.example.payment_service.dto.response.RouteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "route-service", url = "http://localhost:8094/route", configuration = FeignClientConfig.class)
public interface RouteClient {
    @GetMapping(value = "/{id}",
            consumes = "application/json",
            produces = "application/json")
    RouteResponse getRouteById(@PathVariable String id);
}