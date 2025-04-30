package com.example.trip_service.controller;

import com.example.trip_service.dto.request.TripRequest;
import com.example.trip_service.service.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping("/trip")
    public ResponseEntity<?> getAllTrips(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.status(HttpStatus.OK.value()).body(tripService.getTrips(pageNo, pageSize, sortBy));
    }

    @GetMapping("/trip/departureDateTime")
    public ResponseEntity<?> getTripByDepartureDateTime(
            @RequestParam(required = false) String departureDate,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.status(HttpStatus.OK.value()).body(tripService.getTripsByDepartureDateTime(departureDate, from, to ,pageNo, pageSize, sortBy));
    }

    @GetMapping("/trip/departureDate")
    public ResponseEntity<?> getTripsByDepartureDate(
            @RequestParam(required = false) String departureDate,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.status(HttpStatus.OK.value()).body(tripService.getTripsByDepartureDate(departureDate, pageNo, pageSize, sortBy));
    }

    @GetMapping("/trip/{id}")
    public ResponseEntity<?> getTripById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(tripService.getTripById(id));
    }

    @GetMapping("trip/is-existed-with-id/{id}")
    public ResponseEntity<?> isTripExist(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(tripService.isTripExist(id));
    }

    @PostMapping("/trip")
    public ResponseEntity<?> createTrip(@RequestBody @Valid TripRequest tripRequest) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(tripService.createTrip(tripRequest));
    }

    @PutMapping("/trip/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable String id, @RequestBody @Valid TripRequest tripRequest) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(tripService.updateTrip(id, tripRequest));
    }

    @DeleteMapping("/trip/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(tripService.deleteTrip(id));
    }
}
