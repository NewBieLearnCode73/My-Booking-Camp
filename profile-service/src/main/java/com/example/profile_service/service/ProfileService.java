package com.example.profile_service.service;

import com.example.profile_service.dto.request.ProfileCreationRequest;
import com.example.profile_service.dto.response.UserProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    UserProfileResponse createUserProfile(ProfileCreationRequest request);
    UserProfileResponse findProfileByUserId(String user_id);
    UserProfileResponse deleteProfileById(String id);
    UserProfileResponse updateProfileById(String id, ProfileCreationRequest request);
}
