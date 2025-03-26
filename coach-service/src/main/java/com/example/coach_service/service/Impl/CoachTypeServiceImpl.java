package com.example.coach_service.service.Impl;

import com.example.coach_service.dto.request.CoachTypeRequest;
import com.example.coach_service.entity.CoachType;
import com.example.coach_service.mapper.CoachTypeMapper;
import com.example.coach_service.repository.CoachTypeRepository;
import com.example.coach_service.service.CoachTypeService;
import com.example.coach_service.utils.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CoachTypeServiceImpl implements CoachTypeService {

    @Autowired
    private CoachTypeRepository coachTypeRepository;


    @Autowired
    private CoachTypeMapper coachTypeMapper;

    @Override
    @Transactional
    public CoachType addCoachType(CoachTypeRequest coachTypeRequest) {
        return coachTypeRepository.save(coachTypeMapper.toCoachType(coachTypeRequest));
    }

    @Override
    public CoachType getCoachTypeByName(String name) {
        return coachTypeRepository.findByName(name).orElse(null);
    }

    @Override
    public List<CoachType> getAllCoachTypeByType(Type type) {
        return coachTypeRepository.findAllByType(type);
    }

    @Override
    public CoachType getCoachTypeById(String id) {
        return coachTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<CoachType> getAllCoachType() {
        return coachTypeRepository.findAll();
    }

    @Override
    @Transactional
    public CoachType updateCoachTypeByName(String name, CoachType coachType) {
        Optional<CoachType> coachType1 = coachTypeRepository.findByName(name);

        if (coachType1.isPresent()) {
            coachType.setId(coachType1.get().getId());
            return coachTypeRepository.save(coachType);
        }

        return null;
    }

    @Override
    public void deleteCoachTypeByName(String name) {
        Optional<CoachType> coachType = coachTypeRepository.findByName(name);

        if (coachType.isPresent()) {
            coachTypeRepository.delete(coachType.get());
        }
    }

}
