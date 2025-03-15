package com.example.auth_service.service.Impl;

import com.example.auth_service.entity.User;
import com.example.auth_service.handle.CustomRunTimeException;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User saveUserToDatabase(User user) {
        if(userRepository.findUserByUsername(user.getUsername()) != null){
            throw new CustomRunTimeException("User already exists!");
        }
        else if(userRepository.findUserByEmail(user.getEmail()) != null){
            throw new CustomRunTimeException("Email already exists!");
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            return userRepository.save(user);
        }
    }

    @Override
    public Optional<User> findUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return Optional.of(user.get());
        } else {
            throw new CustomRunTimeException("User not found!");
        }
    }

    @Override
    public String getUserIdByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return user.getId();
        } else {
            throw new CustomRunTimeException("User not found!");
        }
    }
}