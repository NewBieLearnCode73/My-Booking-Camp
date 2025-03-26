package com.example.coach_service.repository;

import com.example.coach_service.entity.Coach;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends MongoRepository<Coach, String> {
    Coach findByLicensePlate(String licensePlate);
}
