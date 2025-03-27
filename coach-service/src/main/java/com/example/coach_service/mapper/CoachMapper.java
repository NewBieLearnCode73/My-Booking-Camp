package com.example.coach_service.mapper;


import com.example.coach_service.dto.request.CoachRequest;
import com.example.coach_service.dto.response.CoachResponse;
import com.example.coach_service.entity.Coach;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoachMapper {
    Coach toCoach(CoachRequest coachRequest);
    CoachResponse toCoachResponse(Coach coach);
}
