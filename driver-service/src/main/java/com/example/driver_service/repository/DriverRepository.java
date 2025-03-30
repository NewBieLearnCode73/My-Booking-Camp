package com.example.driver_service.repository;

import com.example.driver_service.entity.Driver;
import com.example.driver_service.utils.DriverStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends MongoRepository<Driver, String> {
    Driver findDriverById(String id);
    Page<Driver> findAllByStatus(DriverStatus status, Pageable pageable);
    Boolean existsByContactNumber(String contactNumber);
}
