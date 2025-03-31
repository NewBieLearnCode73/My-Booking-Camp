package com.example.trip_service.handle;

public class CustomRunTimeException extends RuntimeException{
    public CustomRunTimeException(String msg){
        super(msg);
    }
}
