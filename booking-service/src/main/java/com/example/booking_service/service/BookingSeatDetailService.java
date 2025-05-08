package com.example.booking_service.service;

import com.example.booking_service.dto.response.BookingSeatDetailResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingSeatDetailService {
    List<BookingSeatDetailResponse> getAllBookingSeatDetailsByBookingId(String bookingId);
}
