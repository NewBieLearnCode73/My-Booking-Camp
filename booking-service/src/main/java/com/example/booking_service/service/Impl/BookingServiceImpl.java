package com.example.booking_service.service.Impl;

import com.example.booking_service.dto.request.BookingRequest;
import com.example.booking_service.dto.request.BookingSeatsUpdateRequest;
import com.example.booking_service.dto.request.BookingTripAndStatusRequest;
import com.example.booking_service.dto.request.BookingUpdateStatusRequest;
import com.example.booking_service.dto.response.BookingResponse;
import com.example.booking_service.dto.response.PaginationResponseDTO;
import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.BookingStatusHistory;
import com.example.booking_service.handle.CustomRunTimeException;
import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.repository.BookingHistoryRepository;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.repository.httpclient.TripClient;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    private TripClient tripClient;

    @Autowired
    private BookingMapper bookingMapper;


    @Override
    public PaginationResponseDTO<BookingResponse> getBookings(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Booking> page = bookingRepository.findAll(pageable);

        List<BookingResponse> bookingResponses = page.stream().map(bookingMapper::toBookingResponse).toList();

        return PaginationResponseDTO.<BookingResponse>builder()
                .items(bookingResponses)
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .build();
    }

    @Override
    public PaginationResponseDTO<BookingResponse> getBookingsByTripId(String tripId, int pageNo, int pageSize, String sortBy) {
        if(!bookingRepository.existsByTripId(tripId)){
            throw new CustomRunTimeException("Trip with id "+ tripId +" not found");
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Booking> page = bookingRepository.findAllByTripId(tripId, pageable);


        List<BookingResponse> bookingResponses = page.stream().map(bookingMapper::toBookingResponse).toList();

        return PaginationResponseDTO.<BookingResponse>builder()
                .items(bookingResponses)
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .build();
    }

    @Override
    public PaginationResponseDTO<BookingResponse> getBookingByUserUsername(String userUsername, int pageNo, int pageSize, String sortBy) {
        if (!bookingRepository.existsByUserUsername(userUsername)) {
            throw new CustomRunTimeException("User with username " + userUsername + " not found");
        }


        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Booking> page = bookingRepository.findAllByUserUsername(userUsername, pageable);
        List<BookingResponse> bookingResponses = page.stream().map(bookingMapper::toBookingResponse).toList();

        return PaginationResponseDTO.<BookingResponse>builder()
                .items(bookingResponses)
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .build();
    }

    @Override
    public PaginationResponseDTO<BookingResponse> getBookingsByTripIdWithBookingStatus(BookingTripAndStatusRequest bookingTripAndStatusRequest, int pageNo, int pageSize, String sortBy) {
        if(!bookingRepository.existsByTripId(bookingTripAndStatusRequest.getTripId())){
            throw new CustomRunTimeException("Trip with id "+ bookingTripAndStatusRequest.getTripId() +" not found");
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Booking> page = bookingRepository.findAllByTripIdAndStatus(bookingTripAndStatusRequest.getTripId(), Status.valueOf(bookingTripAndStatusRequest.getStatus()),pageable);

        List<BookingResponse> bookingResponses = page.stream().map(bookingMapper::toBookingResponse).toList();

        return PaginationResponseDTO.<BookingResponse>builder()
                .items(bookingResponses)
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .build();
    }

    @Override
    public BookingResponse getBookingById(String id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking with id "+ id +" not found"));
        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest, String userUsername, String userRole) {
        Booking booking = bookingMapper.toBooking(bookingRequest);

        if(userRole == null || userRole.isEmpty()){
            throw new CustomRunTimeException("User role not found");
        }
        else if(userRole.equals("ROLE_CUSTOMER")){
            booking.setUserUsername(userUsername);
            booking.setCreatedByUserUsername(userUsername);
        }
        else {
            booking.setCreateByEmployeeUsername(userUsername);
        }

        if(!tripClient.isTripExist(booking.getTripId()).isExisted()){
            throw new CustomRunTimeException("Trip with id "+ booking.getTripId() +" not found!");
        }


        List<Booking> existingBooking = bookingRepository.findAllByTripId(booking.getTripId());

       existingBooking.forEach(x -> {
           x.getBookedSeats().stream().filter(seat -> booking.getBookedSeats().contains(seat)).findFirst().ifPresent(seat -> {
               throw new CustomRunTimeException("Seat " + seat + " already booked by booking id " + x.getId());
           });
       });

       booking.setStatus(Status.PENDING);
       booking.setCreatedDate(LocalDate.now());
       booking.setCreatedTime(LocalTime.now());

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toBookingResponse(savedBooking);
    }

    @Override
    public BookingResponse updateBookingStatus(BookingUpdateStatusRequest bookingUpdateStatusRequest, String userUsername) {
        Booking booking = bookingRepository.findById(bookingUpdateStatusRequest.getBookingId()).orElseThrow(() -> new RuntimeException("Booking with id " + bookingUpdateStatusRequest.getBookingId()  + " not found"));

        // Save log to history
        bookingHistoryRepository.save(
                BookingStatusHistory.builder()
                        .bookingId(booking.getId())
                        .oldStatus(booking.getStatus())
                        .newStatus(Status.valueOf(bookingUpdateStatusRequest.getStatus()))
                        .updatedByUsername(userUsername)
                        .updateDate(LocalDate.now())
                        .updateTime(LocalTime.now())
                        .build()
        );

        booking.setStatus(Status.valueOf(bookingUpdateStatusRequest.getStatus()));

        Booking updatedBooking = bookingRepository.save(booking);
        return bookingMapper.toBookingResponse(updatedBooking);
    }

    @Override
    public BookingResponse updateBookingSeats(BookingSeatsUpdateRequest bookingSeatsUpdateRequest) {
        Booking booking = bookingRepository.findById(bookingSeatsUpdateRequest.getBookingId()).orElseThrow(() -> new RuntimeException("Booking with id " + bookingSeatsUpdateRequest.getBookingId()  + " not found"));

        List<Booking> existingBooking = bookingRepository.findAllByTripId(booking.getTripId());

        existingBooking.forEach(x -> {
            x.getBookedSeats().stream().filter(seat -> booking.getBookedSeats().contains(seat)).findFirst().ifPresent(seat -> {
                throw new CustomRunTimeException("Seat " + seat + " already booked by booking id " + x.getId());
            });
        });

        booking.setBookedSeats(bookingSeatsUpdateRequest.getBookedSeats());

        Booking updatedBooking = bookingRepository.save(booking);

        return bookingMapper.toBookingResponse(updatedBooking);
    }
}
