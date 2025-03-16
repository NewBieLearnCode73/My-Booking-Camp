package com.example.profile_service.service.Impl;

import com.example.profile_service.dto.request.ProfileCreationRequest;
import com.example.profile_service.dto.response.UserProfileResponse;
import com.example.profile_service.entity.Profile;
import com.example.profile_service.mapper.ProfileMapper;
import com.example.profile_service.repository.ProfileRepository;
import com.example.profile_service.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public UserProfileResponse createUserProfile(ProfileCreationRequest request) {
        Profile profile = profileMapper.toProfile(request);

        profile = profileRepository.save(profile);

        return profileMapper.toUserProfileResponse(profile);
    }

    @Override
    public UserProfileResponse findProfileById(String id) {
        Profile profile = profileRepository.findById(id).orElse(null);

        return profileMapper.toUserProfileResponse(profile);
    }

    @Override
    public UserProfileResponse deleteProfileById(String id) {
        Profile profile = profileRepository.findById(id).orElse(null);

        profileRepository.delete(profile);

        return profileMapper.toUserProfileResponse(profile);
    }

    @Override
    public UserProfileResponse updateProfileById(String id, ProfileCreationRequest request) {
        Profile profile = profileRepository.findById(id).orElse(null);

        profile = profileMapper.toProfile(request);

        profile = profileRepository.save(profile);

        return profileMapper.toUserProfileResponse(profile);
    }
}
