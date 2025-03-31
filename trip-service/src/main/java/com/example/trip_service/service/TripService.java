package com.example.trip_service.service;

import com.example.trip_service.dto.request.TripRequest;
import com.example.trip_service.dto.response.PaginationResponseDTO;
import com.example.trip_service.dto.response.TripResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public interface TripService {
    PaginationResponseDTO<TripResponse> getTrips(int pageNo, int pageSize, String sortBy);
    PaginationResponseDTO<TripResponse> getTripsByDepartureDateTime(String departureDate, String from, String to, int pageNo, int pageSize, String sortBy);
    PaginationResponseDTO<TripResponse> getTripsByDepartureDate(String departureDate, int pageNo, int pageSize, String sortBy);
    TripResponse getTripById(String id);

    TripResponse createTrip(TripRequest tripRequest);
    TripResponse updateTrip(String id, TripRequest tripRequest);
    String deleteTrip(String id);
}
