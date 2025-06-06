package com.example.coach_service.controller;

import com.example.coach_service.custom.RequireRole;
import com.example.coach_service.dto.request.CoachRequest;
import com.example.coach_service.entity.Coach;
import com.example.coach_service.service.CoachService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoachController {
    @Autowired
    private CoachService coachService;

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF"})
    @GetMapping("/coach")
    public ResponseEntity<?> getAllCoaches(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachService.getAllCoaches(pageNo, pageSize, sortBy));
    }

    @GetMapping("/coach/{id}")
    public ResponseEntity<?> getCoachById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachService.getCoachById(id));
    }


    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF"})
    @GetMapping("/coach/license-plate/{licensePlate}")
    public ResponseEntity<?> getCoachByLicensePlate(@PathVariable String licensePlate) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachService.getCoachByLicensePlate(licensePlate));
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping("/coach")
    public ResponseEntity<?> createCoach(@RequestBody @Valid CoachRequest coachRequest) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachService.createCoach(coachRequest));
    }
}
