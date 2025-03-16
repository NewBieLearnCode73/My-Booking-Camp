package com.example.profile_service.controller;

import com.example.profile_service.dto.request.ProfileCreationRequest;
import com.example.profile_service.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;


    @PostMapping("/profile")
    public ResponseEntity<?> createUserProfile(@RequestBody ProfileCreationRequest request){
        return ResponseEntity.ok().body(profileService.createUserProfile(request));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> findProfileById(@PathVariable String id){
        return ResponseEntity.ok().body(profileService.findProfileById(id));
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity<?> deleteProfileById(@PathVariable String id){
        return ResponseEntity.ok().body(profileService.deleteProfileById(id));
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateProfileById(@PathVariable String id, @RequestBody ProfileCreationRequest request){
        return ResponseEntity.ok().body(profileService.updateProfileById(id, request));
}}
