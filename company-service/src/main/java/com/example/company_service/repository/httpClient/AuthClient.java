package com.example.company_service.repository.httpClient;

import com.example.company_service.config.FeignClientConfig;
import com.example.company_service.dto.response.OwnerExistedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "auth-service", url = "http://localhost:8090/auth", configuration = FeignClientConfig.class)
public interface AuthClient {
    @GetMapping(value = "/is-owner-existed/{ownerId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    OwnerExistedResponse isOwnerExist(@PathVariable String ownerId, @RequestHeader("Authorization") String token);
}