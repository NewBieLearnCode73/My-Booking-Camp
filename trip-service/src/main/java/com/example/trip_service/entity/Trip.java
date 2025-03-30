package com.example.trip_service.entity;


import com.example.trip_service.utils.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "trip")
public class Trip {
    @Id
    private String id;

    private String routeId;
    private String coachId;
    private String driverId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private TripStatus status;
    private Double basePrice;
}
