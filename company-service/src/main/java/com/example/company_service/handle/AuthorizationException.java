package com.example.company_service.handle;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String msg) {
        super(msg);
    }
}
