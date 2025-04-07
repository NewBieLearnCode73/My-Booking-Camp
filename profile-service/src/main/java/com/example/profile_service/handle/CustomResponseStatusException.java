package com.example.profile_service.handle;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class CustomResponseStatusException extends ResponseStatusException {
    public CustomResponseStatusException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
