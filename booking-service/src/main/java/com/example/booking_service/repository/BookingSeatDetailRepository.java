package com.example.booking_service.repository;

import com.example.booking_service.entity.BookingSeatDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatDetailRepository extends MongoRepository<BookingSeatDetail, String> {
    List<BookingSeatDetail> findAllByBookingId(String bookingId);
    void deleteByBookingId(String bookingId);
}
