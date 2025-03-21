package com.example.notification_service.handle;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class GlobalHanldeException {
    // Check All
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> checkAllException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponse);
    }

    // Check Custom
    @ExceptionHandler(CustomRunTimeException.class)
    public ResponseEntity<ErrorResponse> checkAllCustomException(CustomRunTimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
