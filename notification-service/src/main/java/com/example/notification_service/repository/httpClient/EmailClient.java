package com.example.notification_service.repository.httpClient;

import com.example.notification_service.config.FeignClientConfig;
import com.example.notification_service.dto.request.EmailRequest;
import com.example.notification_service.dto.response.EmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "email-service", url = "https://api.brevo.com", configuration = FeignClientConfig.class)
public interface EmailClient {
    @PostMapping(value = "/v3/smtp/email", produces = "application/json")
    EmailResponse sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody EmailRequest body);
}
