package com.example.trip_service.repository;

import com.example.trip_service.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.time.LocalTime;

@Repository
public interface TripRepository extends MongoRepository<Trip, String> {
    @Query("{ 'departureDate' : ?0 , 'departureTime' : { $gte: ?1, $lte: ?2 } }")
    Page<Trip> findByDepartureDateAndTimeRange(LocalDate departureDate, LocalTime from, LocalTime to, Pageable pageable);
    Page<Trip> findByDepartureDate(LocalDate departureDate, Pageable pageable);
    boolean existsById(String id);
}
