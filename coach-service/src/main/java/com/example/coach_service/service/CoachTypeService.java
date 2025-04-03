package com.example.coach_service.service;

import com.example.coach_service.dto.request.CoachRequest;
import com.example.coach_service.dto.request.CoachTypeRequest;
import com.example.coach_service.dto.response.CoachTypeResponse;
import com.example.coach_service.dto.response.PaginationResponseDTO;
import com.example.coach_service.entity.CoachType;
import com.example.coach_service.utils.Type;
import com.example.event.TripValidationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoachTypeService {
     CoachTypeResponse addCoachType(CoachTypeRequest coachTypeRequest);
     CoachTypeResponse getCoachTypeByName(String name);
     PaginationResponseDTO<CoachTypeResponse> getAllCoachTypeByType(Type type, int pageNo, int pageSize, String sortBy);
     CoachTypeResponse getCoachTypeById(String id);
     PaginationResponseDTO<CoachTypeResponse> getAllCoachType(int pageNo, int pageSize, String sortBy);
     CoachTypeResponse updateCoachTypeByName(String name, CoachTypeRequest coachTypeRequest);
     void deleteCoachTypeByName(String name);
}
