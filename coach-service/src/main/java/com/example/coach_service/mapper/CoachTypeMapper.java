package com.example.coach_service.mapper;

import com.example.coach_service.dto.request.CoachTypeRequest;
import com.example.coach_service.dto.response.CoachTypeResponse;
import com.example.coach_service.entity.CoachType;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface CoachTypeMapper {
    CoachType toCoachType(CoachTypeRequest coachTypeRequest);
    CoachTypeResponse toCoachTypeResponse(CoachType coachType);
}
