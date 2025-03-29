package com.example.coach_service.service.Impl;

import com.example.coach_service.dto.request.CoachTypeRequest;
import com.example.coach_service.dto.response.CoachTypeResponse;
import com.example.coach_service.dto.response.PaginationResponseDTO;
import com.example.coach_service.entity.CoachType;
import com.example.coach_service.handle.CustomRunTimeException;
import com.example.coach_service.mapper.CoachTypeMapper;
import com.example.coach_service.repository.CoachTypeRepository;
import com.example.coach_service.service.CoachTypeService;
import com.example.coach_service.utils.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PaginationResponseDTO<CoachTypeResponse> getAllCoachTypeByType(Type type, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<CoachType> coachTypePage = coachTypeRepository.findAllByType(type, pageable);

        List<CoachTypeResponse> coachTypeResponses = coachTypePage.stream().map(coachTypeMapper::toCoachTypeResponse).toList();

        return PaginationResponseDTO.<CoachTypeResponse>builder()
                .items(coachTypeResponses)
                .totalItems(coachTypePage.getTotalElements())
                .totalPages(coachTypePage.getTotalPages())
                .currentPage(coachTypePage.getNumber())
                .pageSize(coachTypePage.getSize())
                .build();
    }

    @Override
    public CoachTypeResponse getCoachTypeById(String id) {
        return coachTypeMapper.toCoachTypeResponse(coachTypeRepository.findById(id).orElse(null));
    }

    @Override
    public PaginationResponseDTO<CoachTypeResponse> getAllCoachType(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<CoachType> coachTypePage = coachTypeRepository.findAll(pageable);

        List<CoachTypeResponse> coachTypeResponses = coachTypePage.stream().map(coachTypeMapper::toCoachTypeResponse).toList();

        return PaginationResponseDTO.<CoachTypeResponse>builder()
                .items(coachTypeResponses)
                .totalItems(coachTypePage.getTotalElements())
                .totalPages(coachTypePage.getTotalPages())
                .currentPage(coachTypePage.getNumber())
                .pageSize(coachTypePage.getSize())
                .build();
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
