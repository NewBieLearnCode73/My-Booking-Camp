package com.example.coach_service.service;

import com.example.coach_service.dto.request.CoachTypeRequest;
import com.example.coach_service.entity.CoachType;
import com.example.coach_service.utils.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoachTypeService {
    public CoachType addCoachType(CoachTypeRequest coachTypeRequest);
    public CoachType getCoachTypeByName(String name);
    public List<CoachType> getAllCoachTypeByType(Type type);
    public CoachType getCoachTypeById(String id);
    public List<CoachType> getAllCoachType();
    public CoachType updateCoachTypeByName(String name, CoachType coachType);
    public void deleteCoachTypeByName(String name);
}
