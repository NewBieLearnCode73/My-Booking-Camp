package com.example.booking_service.repository;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    Page<Booking> findAllByTripId(String tripId, Pageable pageable);
    Page<Booking> findAllByUserUsername(String userUsername, Pageable pageable);
    Page<Booking> findAllByTripIdAndStatus(String tripId, Status status, Pageable pageable);
    Boolean existsByBookedSeats(List<String> bookedSeats);
    Boolean existsByTripId(String tripId);
    Boolean existsByUserUsername(String userUsername);
    List<Booking> findAllByTripId(String tripId);
}
