package com.example.booking_service.service.Impl;

import com.example.booking_service.dto.request.*;
import com.example.booking_service.dto.response.*;
import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.BookingSeatDetail;
import com.example.booking_service.entity.BookingStatusHistory;
import com.example.booking_service.handle.CustomRunTimeException;
import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.repository.BookingHistoryRepository;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.repository.BookingSeatDetailRepository;
import com.example.booking_service.repository.httpclient.TripClient;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    private BookingSeatDetailRepository bookingSeatDetailRepository;

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
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new CustomRunTimeException("Booking with id "+ id +" not found"));
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

        TripResponse tripResponse = tripClient.getTripById(booking.getTripId());

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


        List<BookingSeatDetail> seatDetails = new ArrayList<>();

        for (String seat : savedBooking.getBookedSeats()) {
            BookingSeatDetail detail = BookingSeatDetail.builder()
                    .seatCode(seat)
                    .bookingId(savedBooking.getId())
                    .price(tripResponse.getBasePrice())
                    .build();

            seatDetails.add(detail);
        }

        bookingSeatDetailRepository.saveAll(seatDetails);

        return bookingMapper.toBookingResponse(savedBooking);
    }

    @Override
    public BookingResponse updateBookingStatus(BookingUpdateStatusRequest bookingUpdateStatusRequest, String userUsername) {
        Booking booking = bookingRepository.findById(bookingUpdateStatusRequest.getBookingId()).orElseThrow(() -> new CustomRunTimeException("Booking with id " + bookingUpdateStatusRequest.getBookingId()  + " not found"));

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
        Booking booking = bookingRepository.findById(bookingSeatsUpdateRequest.getBookingId())
                .orElseThrow(() -> new CustomRunTimeException("Booking with id " + bookingSeatsUpdateRequest.getBookingId()  + " not found"));

        List<Booking> existingBooking = bookingRepository.findAllByTripId(booking.getTripId());

        existingBooking.removeIf(x -> x.getId().equals(bookingSeatsUpdateRequest.getBookingId()));

        List<String> newSeats = bookingSeatsUpdateRequest.getBookedSeats();
        existingBooking.forEach(x -> {
            x.getBookedSeats().stream()
                    .filter(newSeats::contains)
                    .findFirst()
                    .ifPresent(seat -> {
                        throw new CustomRunTimeException("Seat " + seat + " already booked by booking id " + x.getId());
                    });
        });

        booking.setBookedSeats(newSeats);

        Booking updatedBooking = bookingRepository.save(booking);

        Double basePrice = tripClient.getTripById(booking.getTripId()).getBasePrice();

        // Cập nhật bảng phụ booking_seat_detail
        bookingSeatDetailRepository.deleteByBookingId(booking.getId());
        List<BookingSeatDetail> newSeatDetails = newSeats.stream()
                .map(seat -> BookingSeatDetail.builder()
                        .seatCode(seat)
                        .bookingId(updatedBooking.getId())
                        .price(basePrice)
                        .build())
                .toList();

        bookingSeatDetailRepository.saveAll(newSeatDetails);
        return bookingMapper.toBookingResponse(updatedBooking);
    }

    @Override
    public BookingExistedResponse isBookingExist(String id) {
        BookingExistedResponse bookingExistedResponse = new BookingExistedResponse();
        bookingExistedResponse.setExisted(bookingRepository.existsById(id));
        return bookingExistedResponse;
    }

    @Override
    public BookingSeatResponse getAllSeats(String bookingId) {
        List<BookingSeatDetail> bookingSeatDetail = bookingSeatDetailRepository.findAllByBookingId(bookingId);

        if (bookingSeatDetail.isEmpty()) {
            throw new CustomRunTimeException("Booking with id " + bookingId + " not found");
        }

        List<SeatWithDiscountResponse> seats = new ArrayList<>();

        for (BookingSeatDetail seatDetail : bookingSeatDetail) {
            seats.add(SeatWithDiscountResponse.builder()
                    .seatCode(seatDetail.getSeatCode())
                    .discountPercent(seatDetail.getDiscountPercent())
                    .price(seatDetail.getPrice())
                    .build());
        }

        return BookingSeatResponse.builder()
                .bookingId(bookingId)
                .seats(seats).build();
    }

    @Override
    public BookingSeatDiscountResponse addDiscount(BookingSeatDiscountRequest bookingSeatDiscountRequest) {
        Booking booking = bookingRepository.findById(bookingSeatDiscountRequest.getBookingId())
                .orElseThrow(() -> new CustomRunTimeException("Booking with id " + bookingSeatDiscountRequest.getBookingId() + " not found"));

        List<BookingSeatDetail> bookingSeatDetails = bookingSeatDetailRepository.findAllByBookingId(booking.getId());

        if (bookingSeatDetails.isEmpty()) {
            throw new CustomRunTimeException("Booking with id " + booking.getId() + " not found");
        }

        List<BookingSeatDetail> seatDetails = new ArrayList<>();

        bookingSeatDiscountRequest.getSeats().forEach(
                seat -> {
                    String seatCode = seat.getSeatCode();
                    if(!booking.getBookedSeats().contains(seatCode)){
                        throw new CustomRunTimeException("Seat with code " + seatCode + " not found in booking");
                    }
                    BookingSeatDetail seatDetail = bookingSeatDetails.stream().filter(detail -> detail.getSeatCode().equals(seatCode))
                            .findFirst()
                            .orElseThrow(() -> new CustomRunTimeException("Seat with code " + seatCode + " not found"));
                    seatDetail.setDiscountPercent(seat.getDiscountPercent());

                    seatDetails.add(seatDetail);
                }
        );

        bookingSeatDetailRepository.saveAll(seatDetails);


        List<BookingSeatDetail> updatedSeatDetails = bookingSeatDetailRepository.findAllByBookingId(booking.getId());

        List<SeatWithDiscountResponse> seats = updatedSeatDetails.stream().map(seatDetail -> SeatWithDiscountResponse.builder()
                .seatCode(seatDetail.getSeatCode())
                .discountPercent(seatDetail.getDiscountPercent())
                .build()).toList();

        return BookingSeatDiscountResponse.builder()
                .bookingId(booking.getId())
                .seats(seats)
                .build();
    }
}
