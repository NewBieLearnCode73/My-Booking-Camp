package com.example.profile_service.mapper;

import com.example.profile_service.dto.request.ProfileCreationRequest;
import com.example.profile_service.dto.response.UserProfileResponse;
import com.example.profile_service.entity.Profile;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(ProfileCreationRequest profileCreationRequest);
    UserProfileResponse toUserProfileResponse(Profile profile);
}
