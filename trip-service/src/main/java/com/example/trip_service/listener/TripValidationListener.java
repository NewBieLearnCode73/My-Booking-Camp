package com.example.trip_service.listener;

import com.example.event.TripValidationResponse;
import com.example.trip_service.dto.response.TripResponse;
import com.example.trip_service.entity.Trip;
import com.example.trip_service.repository.TripRepository;
import com.example.trip_service.utils.TripStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class TripValidationListener {
    @Autowired
    private TripRepository tripRepository;

    private final Map<String, List<TripValidationResponse>> validationResponses = new ConcurrentHashMap<>();

    @KafkaListener(topics = "trip-validation-response", groupId = "trip-service")
    public void handleTripValidationResponse(TripValidationResponse tripValidationResponse){
        String tripId = tripValidationResponse.getTripId();

        validationResponses.compute(tripId, (key, myList) -> {
            if(myList == null) {
                myList = new ArrayList<>();
            }

            myList.add(tripValidationResponse);

            return myList;
        } );


        List<TripValidationResponse> responses = validationResponses.get(tripId);

        if(responses.size() == 3){
            // All three responses have been received
            boolean allValid = responses.stream().allMatch(TripValidationResponse::getIsValid);

            if(allValid){
                saveTripToDatabase(tripId);
            }
            else {
                log.error("Trip {} is not valid", tripId);
            }

            // Clear the list for the next trip
            validationResponses.remove(tripId);
        }
    }

    private void saveTripToDatabase(String id){
        Optional<Trip> trip = tripRepository.findById(id);

        if(trip.isPresent()){
            Trip myTrip = trip.get();
            myTrip.setStatus(TripStatus.SCHEDULED);
            tripRepository.save(myTrip);
        }
        else {
            log.error("Trip not found");
        }
    }

}
