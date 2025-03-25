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
import com.example.event.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;
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

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

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

        // Gọi sang service profile để tạo profile với thông tin user_id
        profileClient.addUserProfile(profileCreationRequest);

        // Tạo response
        var response = registerMapper.toRegisterUserResponse(savedUser);
        response.setFirstName(registerUserRequest.getFirstName());
        response.setLastName(registerUserRequest.getLastName());
        response.setDob(registerUserRequest.getDob());
        response.setCity(registerUserRequest.getCity());
        response.setPhone(registerUserRequest.getPhone());

        User myUser = userRepository.findUserByUsername(savedUser.getUsername());

        String activationUrl = "http://localhost:8080/auth/activate?code=" + myUser.getActivationCode();

        // HTML
        String html = "<html><body>" +
                "Please use this token to activate your account with " + myUser.getEmail() + ":<br>" +
                "<b>" + myUser.getActivationCode() + "</b><br>" +
                "Or you can click this link to activate your account:<br>" +
                "<a href=\"" + activationUrl + "\">Activate Account</a>" +
                "</body></html>";

        log.info("HTML: {}", html);

        NotificationEvent notificationEvent = NotificationEvent.builder()
                        .email(myUser.getEmail()).name(response.getFirstName() + " " + response.getLastName())
                        .subject("Welcome to MyBookingCamp")
                        .htmlContent(html)
                .build();


        kafkaTemplate.send("user-registered", notificationEvent);


        return response;
    }

    @Override
    @Transactional
    public void activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            throw new CustomRunTimeException("Invalid activation code!");
        }


        if(!Objects.equals(user.getActivationCode(), code)){
            throw new CustomRunTimeException("Invalid activation code!");
        }

        user.setActivation(true);
        user.setActivationCode(null);
        userRepository.save(user);
    }

}