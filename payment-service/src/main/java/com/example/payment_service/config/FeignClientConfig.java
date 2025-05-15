package com.example.payment_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-Auth-Username", "system");
            requestTemplate.header("X-Auth-Roles", "ROLE_MASTER");
        };
    }
}
