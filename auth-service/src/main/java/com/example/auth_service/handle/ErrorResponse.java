package com.example.auth_service.handle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    public ErrorResponse(){
        this.timeStamp = System.currentTimeMillis();

    }

    public ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
    }

    public ErrorResponse(String message) {
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
    }
}
