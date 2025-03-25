package com.example.auth_service.service;


import com.example.auth_service.dto.request.RegisterUserRequest;
import com.example.auth_service.dto.response.RegisterUserResponse;
import com.example.auth_service.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    public Optional<User> findUserById(String id);
    public String getUserIdByUsername(String username);
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
    public void activateUser(String code);
}
