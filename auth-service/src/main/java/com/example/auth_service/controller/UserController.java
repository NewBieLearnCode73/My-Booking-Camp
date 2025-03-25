package com.example.auth_service.controller;

import com.example.auth_service.dto.request.RegisterUserRequest;
import com.example.auth_service.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    // Register
    @PermitAll
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(registerUserRequest));
    }

    @PermitAll
    @GetMapping("/auth/activate")
    public ResponseEntity<?> activateUser(@RequestParam String code){
        userService.activateUser(code);
        return ResponseEntity.status(HttpStatus.OK).body("User activated successfully");
    }
}
