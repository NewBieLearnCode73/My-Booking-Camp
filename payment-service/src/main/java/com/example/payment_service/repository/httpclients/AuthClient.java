package com.example.payment_service.repository.httpclients;

import com.example.payment_service.config.FeignClientConfig;
import com.example.payment_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", url = "http://localhost:8090/auth", configuration = FeignClientConfig.class)
public interface AuthClient {
    @GetMapping(value = "/get-basic-info-by-username/{username}",
            consumes = "application/json",
            produces = "application/json")
    UserProfileResponse findProfileByUsername(@PathVariable String username);

    @GetMapping(value = "/get-username-by-id/{id}",
            consumes = "application/json",
            produces = "application/json")
    String getUsernameById(@PathVariable String id);
}