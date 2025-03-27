package com.example.coach_service.service.Impl;

import com.example.coach_service.dto.request.CoachTypeRequest;
import com.example.coach_service.dto.response.CoachTypeResponse;
import com.example.coach_service.entity.CoachType;
import com.example.coach_service.handle.CustomRunTimeException;
import com.example.coach_service.mapper.CoachTypeMapper;
import com.example.coach_service.repository.CoachTypeRepository;
import com.example.coach_service.service.CoachTypeService;
import com.example.coach_service.utils.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Arrays;
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
    public CoachTypeResponse addCoachType(CoachTypeRequest coachTypeRequest) {
        return coachTypeMapper.toCoachTypeResponse(coachTypeRepository.save(coachTypeMapper.toCoachType(coachTypeRequest)));
    }

    @Override
    public CoachTypeResponse getCoachTypeByName(String name) {
        return coachTypeMapper.toCoachTypeResponse(coachTypeRepository.findByName(name).orElse(null));
    }

    @Override
    public List<CoachTypeResponse> getAllCoachTypeByType(Type type) {
        List<CoachType> myCoachTypeList = coachTypeRepository.findAllByType(type);

        return myCoachTypeList.stream().map(coachTypeMapper::toCoachTypeResponse).toList();
    }

    @Override
    public CoachTypeResponse getCoachTypeById(String id) {
        return coachTypeMapper.toCoachTypeResponse(coachTypeRepository.findById(id).orElse(null));
    }

    @Override
    public List<CoachTypeResponse> getAllCoachType() {
        List<CoachType> myCoachTypeList = coachTypeRepository.findAll();

        return myCoachTypeList.stream().map(coachTypeMapper::toCoachTypeResponse).toList();
    }

    @Override
    public CoachTypeResponse updateCoachTypeByName(String name, CoachTypeRequest coachTypeRequest) {

        Optional<CoachType> coachType = coachTypeRepository.findByName(name);

        if (coachType.isEmpty()) {
            throw new CustomRunTimeException("Coach Type with name " + name + " not found");
        }

        if (Arrays.stream(EnumUtils.class.getClasses()).noneMatch(e -> e.getName().equals(coachTypeRequest.getType()))) {
            throw new CustomRunTimeException("Invalid type");
        }

        coachType.get().setType(Type.valueOf(coachTypeRequest.getType()));
        coachType.get().setSeatLayout(coachTypeRequest.getSeatLayout());

        return coachTypeMapper.toCoachTypeResponse(coachTypeRepository.save(coachType.get()));
    }


    @Override
    public void deleteCoachTypeByName(String name) {
        Optional<CoachType> coachType = coachTypeRepository.findByName(name);

        if (coachType.isEmpty()) {
            throw new CustomRunTimeException("Coach Type with name " + name + " not found");
        }

        coachTypeRepository.delete(coachType.get());
    }

}
