package com.example.auth_service.service.Impl;

import com.example.auth_service.dto.request.RegisterUserRequest;
import com.example.auth_service.dto.response.CompanyExistedResponse;
import com.example.auth_service.dto.response.OwnerExistedResponse;
import com.example.auth_service.dto.response.RegisterUserResponse;
import com.example.auth_service.dto.response.UserProfileResponse;
import com.example.auth_service.entity.User;
import com.example.auth_service.handle.CustomRunTimeException;
import com.example.auth_service.mapper.ProfileMapper;
import com.example.auth_service.mapper.RegisterMapper;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.repository.httpclient.CompanyClient;
import com.example.auth_service.repository.httpclient.ProfileClient;
import com.example.auth_service.service.UserService;
import com.example.auth_service.utils.Role;
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
    private CompanyClient companyClient;

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


        NotificationEvent notificationEvent = NotificationEvent.builder()
                        .email(myUser.getEmail()).name(response.getFirstName() + " " + response.getLastName())
                        .subject("Welcome to MyBookingCamp")
                        .htmlContent(html)
                .build();


        log.info("Prepare to send email: {}", myUser.getEmail());
        kafkaTemplate.send("user-registered", notificationEvent);
        log.info("Sent email: {}", myUser.getEmail());

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

    @Override
    @Transactional
    public String changeCustomerToStaff(String id, String companyId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomRunTimeException("User with id "+ id  +" not found!"));

        CompanyExistedResponse companyExistedResponse = companyClient.isCompanyExist(companyId);

        if(!companyExistedResponse.isExisted()) {
            throw new CustomRunTimeException("Company with id "+ companyId  +" not found!");
        }
        if (user.getCompanyId() != null) {
            throw new CustomRunTimeException("User with id "+ id  +" is already a staff of company with id "+ user.getCompanyId() +"!");
        }
        if (user.getRole() == Role.STAFF) {
            throw new CustomRunTimeException("User with id "+ id  +" is already a staff!");
        }
        if (user.getRole() == Role.ADMIN){
            throw new CustomRunTimeException("User with id "+ id  +" is an admin, cannot change to staff!");
        }
        if(user.getRole() == Role.OWNER){
            throw new CustomRunTimeException("User with id "+ id  +" is an owner, cannot change to staff!");
        }
        else {
            user.setRole(Role.STAFF);
            user.setCompanyId(companyId);
            userRepository.save(user);
            return "User with id "+ id  +" has been changed to staff!";
        }
    }

    @Override
    public OwnerExistedResponse isOwnerExist(String ownerId) {
        OwnerExistedResponse ownerExistedResponse = new OwnerExistedResponse();
        User user = userRepository.findById(ownerId)
                .orElseThrow(() -> new CustomRunTimeException("User with id "+ ownerId  +" not found!"));
        if (user.getRole() == Role.OWNER) {
            ownerExistedResponse.setExisted(true);
            ownerExistedResponse.setMessage("User with id "+ ownerId  +" is an owner!");
        } else {
            ownerExistedResponse.setExisted(false);
            ownerExistedResponse.setMessage("User with id "+ ownerId  +" is not an owner!");
        }
        return ownerExistedResponse;

    }

    @Override
    public String changeCustomerToOwner(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomRunTimeException("User with id "+ id  +" not found!"));

        if (user.getRole() == Role.OWNER) {
            throw new CustomRunTimeException("User with id "+ id  +" is already an owner!");
        }
        if (user.getRole() == Role.STAFF){
            throw new CustomRunTimeException("User with id "+ id  +" is a staff, cannot change to owner!");
        }
        else {
            user.setRole(Role.OWNER);
            userRepository.save(user);
            return "User with id "+ id  +" has been changed to owner!";
        }
    }

    @Override
    public UserProfileResponse getBasicInfoByUsername(String username) {
        String userId = this.getUserIdByUsername(username);

        if (userId == null || userId.isEmpty()) {
            throw new CustomRunTimeException("User not found!");
        }

        UserProfileResponse userProfileResponse = profileClient.findProfileByUserId(userId);

        if (userProfileResponse == null) {
            throw new CustomRunTimeException("User profile not found!");
        }

        return userProfileResponse;
    }

    @Override
    public String getUsernameById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomRunTimeException("User with id "+ id  +" not found!"));
        if (user.getUsername() == null) {
            throw new CustomRunTimeException("Username not found!");
        }
        return user.getUsername();
    }
}