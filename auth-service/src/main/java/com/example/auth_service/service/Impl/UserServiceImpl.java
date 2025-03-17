package com.example.auth_service.service.Impl;

import com.example.auth_service.dto.request.RegisterUserRequest;
import com.example.auth_service.dto.response.RegisterUserResponse;
import com.example.auth_service.entity.User;
import com.example.auth_service.handle.CustomRunTimeException;
import com.example.auth_service.mapper.ProfileMapper;
import com.example.auth_service.mapper.RegisterMapper;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.repository.httpclient.ProfileClient;
import com.example.auth_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileClient profileClient;

    @Override
    public Optional<User> findUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user;
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

    @Override
    @Transactional
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {

        if (userRepository.findUserByUsername(registerUserRequest.getUsername()) != null) {
            throw new CustomRunTimeException("User already exists!");
        }

        if (userRepository.findUserByEmail(registerUserRequest.getEmail()) != null) {
            throw new CustomRunTimeException("Email already exists!");
        }

        User user = registerMapper.toUser(registerUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user); // Thêm user vào db

        // Tạo profile cho user
        var profileCreationRequest = profileMapper.toProfileCreationRequest(registerUserRequest);


        // Set user_id cho profileCreationRequest
        profileCreationRequest.setUser_id(savedUser.getId());

        log.info("ProfileCreationRequest: {}", profileCreationRequest);

        // Gọi sang service profile để tạo profile với thông tin user_id
        profileClient.addUserProfile(profileCreationRequest);

        // Tạo response
        var response = registerMapper.toRegisterUserResponse(savedUser);
        response.setFirstName(registerUserRequest.getFirstName());
        response.setLastName(registerUserRequest.getLastName());
        response.setDob(registerUserRequest.getDob());
        response.setCity(registerUserRequest.getCity());
        response.setPhone(registerUserRequest.getPhone());
        return response;
    }

}