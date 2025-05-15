package com.example.payment_service.repository.httpclients;

import com.example.payment_service.config.FeignClientConfig;
import com.example.payment_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", url = "http://localhost:8091/profile/users", configuration = FeignClientConfig.class)
public interface ProfileClient {
    @GetMapping(value = "/{id}",
            consumes = "application/json",
            produces = "application/json")
    UserProfileResponse findProfileByUserId(@PathVariable String id);
}