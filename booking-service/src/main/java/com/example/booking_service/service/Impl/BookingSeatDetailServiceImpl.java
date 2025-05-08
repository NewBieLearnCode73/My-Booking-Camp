package com.example.booking_service.service.Impl;

import com.example.booking_service.dto.response.BookingSeatDetailResponse;
import com.example.booking_service.entity.BookingSeatDetail;
import com.example.booking_service.handle.CustomRunTimeException;
import com.example.booking_service.mapper.BookingSeatDetailMapper;
import com.example.booking_service.repository.BookingSeatDetailRepository;
import com.example.booking_service.service.BookingSeatDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingSeatDetailServiceImpl implements BookingSeatDetailService {

    @Autowired
    private BookingSeatDetailRepository bookingSeatDetailRepository;
    @Autowired
    private BookingSeatDetailMapper bookingSeatDetailMapper;

    @Override
    public List<BookingSeatDetailResponse> getAllBookingSeatDetailsByBookingId(String bookingId) {
        List<BookingSeatDetail> bookingSeatDetails = bookingSeatDetailRepository.findAllByBookingId(bookingId);

        if (bookingSeatDetails.isEmpty()) {
            throw new CustomRunTimeException("No booking seat details found for booking ID: " + bookingId);
        }

        return bookingSeatDetails.stream()
                .map(bookingSeatDetailMapper::toBookingSeatDetailResponse)
                .toList();
    }
}
