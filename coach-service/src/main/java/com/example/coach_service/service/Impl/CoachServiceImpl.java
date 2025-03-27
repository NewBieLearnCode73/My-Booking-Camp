package com.example.coach_service.service.Impl;

import com.example.coach_service.entity.Coach;
import com.example.coach_service.entity.CoachType;
import com.example.coach_service.handle.CustomRunTimeException;
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


    @Override
    public Coach createCoach(Coach coach) {
        Optional<CoachType> myCoachType = coachTypeRepository.findById(coach.getCoachTypeId());

        if(!myCoachType.isPresent()){
            throw new CustomRunTimeException("Coach Type not found");
        }

        return coachRepository.save(coach);
    }

    @Override
    public void updateCoachById(String id, Coach coach) {
        Optional<Coach> myCoach = coachRepository.findById(id);

        if(!myCoach.isPresent()){
            throw new CustomRunTimeException("Coach not found");
        }

        Optional<CoachType> myCoachType = coachTypeRepository.findById(coach.getCoachTypeId());

        if(!myCoachType.isPresent()){
            throw new CustomRunTimeException("Coach Type not found");
        }

        coachRepository.save(coach);
    }

    @Override
    public void deleteCoachById(String id) {
        Optional<Coach> myCoach = coachRepository.findById(id);

        if(!myCoach.isPresent()){
            throw new CustomRunTimeException("Coach not found");
        }

        coachRepository.deleteById(id);
    }

    @Override
    public Coach getCoachById(String id) {
        Optional<Coach> myCoach = coachRepository.findById(id);

        if(!myCoach.isPresent()){
            throw new CustomRunTimeException("Coach not found");
        }

        return myCoach.get();
    }

    @Override
    public Coach getCoachByLicensePlate(String licensePlate) {
        Coach myCoach = coachRepository.findByLicensePlate(licensePlate);

        if(myCoach == null){
            throw new CustomRunTimeException("Coach not found");
        }

        return myCoach;
    }

    @Override
    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }
}
