package com.example.coach_service.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@Slf4j
public class GlobalHandleException {

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

    // Check Valid Constraint
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> checkValidConstraint(MethodArgumentNotValidException ex){
        String message = ex.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponse);
    }
}
