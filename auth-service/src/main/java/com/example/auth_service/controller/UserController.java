package com.example.auth_service.controller;

import com.example.auth_service.entity.User;
import com.example.auth_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUserToDatabase(user));
    }

    @GetMapping("/api/v1/user/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> findUserById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }

    // Register
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUserToDatabase(user));
    }
}
