package com.example.auth_service.service;


import com.example.auth_service.dto.request.RegisterUserRequest;
import com.example.auth_service.dto.response.OwnerExistedResponse;
import com.example.auth_service.dto.response.RegisterUserResponse;
import com.example.auth_service.dto.response.UserProfileResponse;
import com.example.auth_service.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
     Optional<User> findUserById(String id);
     String getUserIdByUsername(String username);
     RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
     void activateUser(String code);
     String changeCustomerToStaff(String id, String companyId);
     OwnerExistedResponse isOwnerExist(String ownerId);
     String changeCustomerToOwner(String id);
     UserProfileResponse getBasicInfoByUsername(String username);
}
