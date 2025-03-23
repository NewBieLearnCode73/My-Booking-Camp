package com.example.auth_service.mapper;

import com.example.auth_service.dto.request.ProfileCreationRequest;
import com.example.auth_service.dto.request.RegisterUserRequest;
import com.example.auth_service.dto.response.RegisterUserResponse;
import com.example.auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    @Mapping(target = "activation" , constant = "false")
    @Mapping(target = "role" , constant = "CUSTOMER")
    User toUser(RegisterUserRequest registerUserRequest);

    RegisterUserResponse toRegisterUserResponse(User user);


}
