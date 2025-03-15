package com.example.auth_service.service;


import com.example.auth_service.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {
    public User saveUserToDatabase(User user);
    public Optional<User> findUserById(String id);
    public String getUserIdByUsername(String username);
}
