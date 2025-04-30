package com.example.trip_service.service.Impl;

import com.example.event.TripValidationRequest;
import com.example.trip_service.dto.request.TripRequest;
import com.example.trip_service.dto.response.PaginationResponseDTO;
import com.example.trip_service.dto.response.TripExistResponse;
import com.example.trip_service.dto.response.TripResponse;
import com.example.trip_service.entity.Trip;
import com.example.trip_service.handle.CustomRunTimeException;
import com.example.trip_service.mapper.TripMapper;
import com.example.trip_service.repository.TripRepository;
import com.example.trip_service.service.TripService;
import com.example.trip_service.utils.TripStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripMapper tripMapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private Trip tempTrip;

    @Override
    public PaginationResponseDTO<TripResponse> getTrips(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Trip> page = tripRepository.findAll(pageable);

        List<TripResponse> tripResponses = page.stream().map(tripMapper::tripToTripResponse).toList();

        return PaginationResponseDTO.<TripResponse>builder()
                .items(tripResponses)
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .build();
    }

    @Override
    public PaginationResponseDTO<TripResponse> getTripsByDepartureDateTime(String departureDate, String from, String to, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        LocalDate departureDateObj;
        LocalTime fromObj;
        LocalTime toObj;

        if(departureDate == null || from == null || to == null) {
            departureDateObj = LocalDate.now();
            fromObj = LocalTime.now();
            toObj = LocalTime.now();
        } else {
            departureDateObj = LocalDate.parse(departureDate);
            fromObj = LocalTime.parse(from);
            toObj = LocalTime.parse(to);
        }

        Page<Trip> page = tripRepository.findByDepartureDateAndTimeRange(departureDateObj, fromObj, toObj, pageable);

        List<TripResponse> tripResponses = page.stream().map(tripMapper::tripToTripResponse).toList();

        return PaginationResponseDTO.<TripResponse>builder()
                .items(tripResponses)
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .build();
    }

    @Override
    public PaginationResponseDTO<TripResponse> getTripsByDepartureDate(String departureDate, int pageNo, int pageSize, String sortBy) {

        LocalDate departureDateObj ;

        if (departureDate == null) {
            departureDateObj = LocalDate.now();
        } else {
            departureDateObj = LocalDate.parse(departureDate);
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Trip> page = tripRepository.findByDepartureDate(departureDateObj, pageable);

        List<TripResponse> tripResponses = page.stream().map(tripMapper::tripToTripResponse).toList();

        return PaginationResponseDTO.<TripResponse>builder()
                .items(tripResponses)
                .currentPage(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .build();
    }

    @Override
    public TripResponse getTripById(String id) {
        Optional<Trip> trip = Optional.ofNullable(tripRepository.findById(id).orElseThrow(() -> new CustomRunTimeException("Trip with id " + id + "not found")));

        if(trip.isEmpty()) {
            throw new RuntimeException("Trip with id "+ id +"not found");
        }

        return tripMapper.tripToTripResponse(trip.get());
    }

    @Override
    public TripExistResponse isTripExist(String id) {
        boolean trip = tripRepository.existsById(id);

        return TripExistResponse.builder().existed(trip).message("Trip with id " + id + (trip ? " exists" : " does not exist")).build();
    }

    @Override
    public String createTrip(TripRequest tripRequest) {
        Trip trip = tripMapper.tripRequestToTrip(tripRequest);

        TripValidationRequest tripValidationRequest = new TripValidationRequest();

        tripValidationRequest.setTripId(UUID.randomUUID().toString());
        tripValidationRequest.setRouteId(tripRequest.getRouteId());
        tripValidationRequest.setDriverId(tripRequest.getDriverId());
        tripValidationRequest.setCoachId(tripRequest.getCoachId());

        trip.setId(tripValidationRequest.getTripId());


        trip.setStatus(TripStatus.PENDING);
        tripRepository.save(trip);


        kafkaTemplate.send("validate-coach", tripValidationRequest);
        kafkaTemplate.send("validate-driver", tripValidationRequest);
        kafkaTemplate.send("validate-route", tripValidationRequest);
        
        return "Trip validation in progress, waiting for confirmation.";
    }

    @Override
    public TripResponse updateTrip(String id, TripRequest tripRequest) {
        Optional<Trip> trip = Optional.ofNullable(tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip with id " + id + " not found")));

        if(trip.isEmpty()) {
            throw new RuntimeException("Trip with id "+ id +"not found");
        }

        Trip tripToUpdate = trip.get();

        tripToUpdate.setCoachId(tripRequest.getCoachId());
        tripToUpdate.setDriverId(tripRequest.getDriverId());
        tripToUpdate.setDepartureDate(LocalDate.parse(tripRequest.getDepartureDate()));
        tripToUpdate.setDepartureTime(LocalTime.parse(tripRequest.getDepartureTime()));
        tripToUpdate.setArrivalDate(LocalDate.parse(tripRequest.getArrivalDate()));
        tripToUpdate.setArrivalTime(LocalTime.parse(tripRequest.getArrivalTime()));
        tripToUpdate.setStatus(TripStatus.valueOf(tripRequest.getStatus()));
        tripToUpdate.setBasePrice(tripRequest.getBasePrice());

        return tripMapper.tripToTripResponse(tripRepository.save(tripToUpdate));
    }

    @Override
    public String deleteTrip(String id) {
        Optional<Trip> trip = tripRepository.findById(id);

        if(trip.isEmpty()) {
            throw new CustomRunTimeException("Trip with id "+ id +" not found");
        }

        tripRepository.delete(trip.get());

        return "Delete trip with id " + id + " successfully";
    }
}
