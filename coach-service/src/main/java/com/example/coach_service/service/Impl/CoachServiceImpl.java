package com.example.coach_service.service.Impl;

import com.example.coach_service.dto.request.CoachRequest;
import com.example.coach_service.dto.response.CoachResponse;
import com.example.coach_service.entity.Coach;
import com.example.coach_service.entity.CoachType;
import com.example.coach_service.handle.CustomRunTimeException;
import com.example.coach_service.mapper.CoachMapper;
import com.example.coach_service.repository.CoachRepository;
import com.example.coach_service.repository.CoachTypeRepository;
import com.example.coach_service.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private CoachTypeRepository coachTypeRepository;

    @Autowired
    private CoachMapper coachMapper;

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
    public List<CoachResponse> getAllCoaches() {
        return coachRepository.findAll().stream().map(coachMapper::toCoachResponse).toList();
    }
}
