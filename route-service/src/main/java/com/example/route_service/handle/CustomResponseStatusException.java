package com.example.route_service.handle;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


public class CustomResponseStatusException extends ResponseStatusException {
    public CustomResponseStatusException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
