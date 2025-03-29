package com.example.coach_service.service;

import com.example.coach_service.dto.request.CoachRequest;
import com.example.coach_service.dto.response.CoachResponse;
import com.example.coach_service.dto.response.PaginationResponseDTO;
import com.example.coach_service.entity.Coach;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoachService {
    CoachResponse createCoach(CoachRequest coachRequest);
    void updateCoachById(String id, CoachRequest coachRequest);
    void deleteCoachById(String id);
    CoachResponse getCoachById(String id);
    CoachResponse getCoachByLicensePlate(String licensePlate);
    PaginationResponseDTO<CoachResponse> getAllCoaches(int pageNo, int pageSize, String sortBy);
}
