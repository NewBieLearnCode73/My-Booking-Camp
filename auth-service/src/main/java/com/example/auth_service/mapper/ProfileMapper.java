package com.example.auth_service.mapper;

import com.example.auth_service.dto.request.ProfileCreationRequest;
import com.example.auth_service.dto.request.RegisterUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(RegisterUserRequest registerUserRequest);
}
