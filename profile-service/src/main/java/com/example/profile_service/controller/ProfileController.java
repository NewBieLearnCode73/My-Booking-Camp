package com.example.profile_service.controller;

import com.example.profile_service.custom.RequireRole;
import com.example.profile_service.dto.request.ProfileCreationRequest;
import com.example.profile_service.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF", "ROLE_CUSTOMER"})
    @GetMapping("/profile/users/{user_id}")
    public ResponseEntity<?> findProfileByUserId(@PathVariable String user_id){
        return ResponseEntity.ok().body(profileService.findProfileByUserId(user_id));
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF"})
    @PostMapping("/profile/users")
    public ResponseEntity<?> createUserProfile(@RequestBody ProfileCreationRequest request){
        return ResponseEntity.ok().body(profileService.createUserProfile(request));
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER"})
    @DeleteMapping("/profile/users/{id}")
    public ResponseEntity<?> deleteProfileById(@PathVariable String id){
        return ResponseEntity.ok().body(profileService.deleteProfileById(id));
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF", "ROLE_CUSTOMER"})
    @PutMapping("/profile/users/{id}")
    public ResponseEntity<?> updateProfileById(@PathVariable String id, @RequestBody ProfileCreationRequest request){
        return ResponseEntity.ok().body(profileService.updateProfileById(id, request));
}}
