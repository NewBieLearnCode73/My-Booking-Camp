package com.example.coach_service.service.Impl;

import com.example.coach_service.dto.request.CoachRequest;
import com.example.coach_service.dto.response.CoachResponse;
import com.example.coach_service.dto.response.PaginationResponseDTO;
import com.example.coach_service.entity.Coach;
import com.example.coach_service.entity.CoachType;
import com.example.coach_service.handle.CustomRunTimeException;
import com.example.coach_service.mapper.CoachMapper;
import com.example.coach_service.repository.CoachRepository;
import com.example.coach_service.repository.CoachTypeRepository;
import com.example.coach_service.service.CoachService;
import com.example.event.TripValidationRequest;
import com.example.event.TripValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private CoachTypeRepository coachTypeRepository;

    @Autowired
    private CoachMapper coachMapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public CoachResponse createCoach(CoachRequest coachRequest) {
        Coach coach = coachMapper.toCoach(coachRequest);

        Optional<CoachType> myCoachType = coachTypeRepository.findById(coach.getCoachTypeId());

        if(myCoachType.isEmpty()){
            throw new CustomRunTimeException("Coach Type not found");
        }

        return coachMapper.toCoachResponse(coachRepository.save(coach));
    }

    @Override
    public void updateCoachById(String id, CoachRequest coachRequest) {

        Coach coach = coachMapper.toCoach(coachRequest);

        Optional<Coach> myCoach = coachRepository.findById(id);

        if(myCoach.isEmpty()){
            throw new CustomRunTimeException("Coach not found");
        }

        Optional<CoachType> myCoachType = coachTypeRepository.findById(coach.getCoachTypeId());

        if(myCoachType.isEmpty()){
            throw new CustomRunTimeException("Coach Type not found");
        }

        coachRepository.save(coach);
    }

    @Override
    public void deleteCoachById(String id) {
        Optional<Coach> myCoach = coachRepository.findById(id);

        if(myCoach.isEmpty()){
            throw new CustomRunTimeException("Coach not found");
        }

        coachRepository.deleteById(id);
    }

    @Override
    public CoachResponse getCoachById(String id) {
        Optional<Coach> myCoach = coachRepository.findById(id);

        if(myCoach.isEmpty()){
            throw new CustomRunTimeException("Coach not found");
        }

        return coachMapper.toCoachResponse(myCoach.get());
    }

    @Override
    public CoachResponse getCoachByLicensePlate(String licensePlate) {
        Coach myCoach = coachRepository.findByLicensePlate(licensePlate);

        if(myCoach == null){
            throw new CustomRunTimeException("Coach not found");
        }

        return coachMapper.toCoachResponse(myCoach);
    }

    @Override
    public PaginationResponseDTO<CoachResponse> getAllCoaches(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Coach> coachPage = coachRepository.findAll(pageable);

        List<CoachResponse> coachResponses = coachPage.stream().map(coachMapper::toCoachResponse).toList();

        return PaginationResponseDTO.<CoachResponse>builder()
                .items(coachResponses)
                .totalItems(coachPage.getTotalElements())
                .totalPages(coachPage.getTotalPages())
                .currentPage(coachPage.getNumber())
                .pageSize(coachPage.getSize())
                .build();

    }

    @Override
    @KafkaListener(topics = "validate-coach", groupId = "coach-service")
    public void validateRoute(TripValidationRequest tripValidationRequest) {
        boolean isCoachValid = coachRepository.existsById(tripValidationRequest.getCoachId());

        log.info("Coach validation result: {}", isCoachValid);

        TripValidationResponse tripValidationResponse = new TripValidationResponse();

        tripValidationResponse.setTripId(tripValidationRequest.getTripId());
        tripValidationResponse.setType("coach");
        tripValidationResponse.setIsValid(isCoachValid);

        kafkaTemplate.send("trip-validation-response", tripValidationResponse);
    }
}
