package com.example.trip_service.mapper;

import com.example.trip_service.dto.request.TripRequest;
import com.example.trip_service.dto.response.TripResponse;
import com.example.trip_service.entity.Trip;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TripMapper  {
    Trip tripRequestToTrip(TripRequest tripRequest);
    TripResponse tripToTripResponse(Trip trip);
}
