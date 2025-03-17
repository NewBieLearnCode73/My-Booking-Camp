package com.example.auth_service.repository.httpclient;

import com.example.auth_service.dto.request.ProfileCreationRequest;
import com.example.auth_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "http://localhost:8080/profile")
public interface ProfileClient {
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse addUserProfile(@RequestBody ProfileCreationRequest profileCreationRequest);

}
