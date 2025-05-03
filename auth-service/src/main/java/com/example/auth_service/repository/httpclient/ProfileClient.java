package com.example.auth_service.repository.httpclient;

import com.example.auth_service.dto.request.ProfileCreationRequest;
import com.example.auth_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "http://localhost:8091/profile")
public interface ProfileClient {
    @PostMapping(value = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse addUserProfile(@RequestBody ProfileCreationRequest profileCreationRequest);

    @GetMapping(value = "/users/{user_id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse findProfileByUserId(@PathVariable String user_id);
}
