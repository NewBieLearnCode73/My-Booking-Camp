package com.example.booking_service.service;

import com.example.booking_service.dto.request.*;
import com.example.booking_service.dto.response.*;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    PaginationResponseDTO<BookingResponse> getBookings(int pageNo, int pageSize, String sortBy);
    PaginationResponseDTO<BookingResponse> getBookingsByTripId(String tripId, int pageNo, int pageSize, String sortBy);
    PaginationResponseDTO<BookingResponse> getBookingByUserUsername(String userUsername, int pageNo, int pageSize, String sortBy);
    PaginationResponseDTO<BookingResponse> getBookingsByTripIdWithBookingStatus(BookingTripAndStatusRequest bookingTripAndStatusRequest, int pageNo, int pageSize, String sortBy);

    BookingResponse getBookingById(String id);
    BookingResponse createBooking(BookingRequest bookingRequest, String userUsername, String userRole);
    BookingResponse updateBookingStatus(BookingUpdateStatusRequest bookingUpdateStatusRequest, String userUsername);
    BookingResponse updateBookingSeats(BookingSeatsUpdateRequest bookingSeatsUpdateRequest);

    BookingExistedResponse isBookingExist(String id);
    BookingSeatResponse getAllSeats(String bookingId);
    BookingSeatBookedResponse getAllSeatWasBookedByTripId(String tripId);

    BookingSeatDiscountResponse addDiscount(BookingSeatDiscountRequest bookingSeatDiscountRequest);
}