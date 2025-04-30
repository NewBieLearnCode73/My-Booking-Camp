package com.example.booking_service.repository;

import com.example.booking_service.entity.BookingStatusHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingHistoryRepository extends MongoRepository<BookingStatusHistory, String> {
    Page<BookingStatusHistory> findAllByBookingId(String bookingId, Pageable pageable);
}
