package com.example.coach_service.controller;

import com.example.coach_service.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoachController {
    @Autowired
    private CoachService coachService;

    @GetMapping("/coach")
    public ResponseEntity<?> getAllCoaches() {
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachService.getAllCoaches());
    }

    @PostMapping("/coach")
    public ResponseEntity<?> createCoach() {
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachService.createCoach());
    }

    @GetMapping("/coach/{id}")
    public ResponseEntity<?> getCoachById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachService.getCoachById(id));
    }

    @PostMapping("/coach/license-plate/{licensePlate}")
    public ResponseEntity<?> getCoachByLicensePlate(@PathVariable String licensePlate) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(coachService.getCoachByLicensePlate(licensePlate));
    }
}
