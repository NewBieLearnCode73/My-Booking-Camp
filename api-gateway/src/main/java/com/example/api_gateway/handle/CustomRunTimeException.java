package com.example.api_gateway.handle;

import org.springframework.http.HttpStatus;

public class CustomRunTimeException extends RuntimeException{
    public CustomRunTimeException(HttpStatus unauthorized, String msg){
        super(msg);
    }
}
