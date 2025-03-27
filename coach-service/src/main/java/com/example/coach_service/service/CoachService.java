package com.example.coach_service.service;

import com.example.coach_service.entity.Coach;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoachService {
    Coach createCoach(Coach coach);
    void updateCoachById(String id, Coach coach);
    void deleteCoachById(String id);
    Coach getCoachById(String id);
    Coach getCoachByLicensePlate(String licensePlate);
    List<Coach> getAllCoaches();
}
