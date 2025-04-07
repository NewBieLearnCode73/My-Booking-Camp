package com.example.payment_service.handle;

public class CustomRunTimeException extends RuntimeException {
    public CustomRunTimeException(String message) {
        super(message);
    }
}
