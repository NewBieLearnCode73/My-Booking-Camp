package com.example.coach_service.repository;

import com.example.coach_service.entity.CoachType;
import com.example.coach_service.utils.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachTypeRepository extends MongoRepository<CoachType, String> {
    Optional<CoachType> findByName(String name);
    Page<CoachType> findAllByType(Type type, Pageable pageable);
}
