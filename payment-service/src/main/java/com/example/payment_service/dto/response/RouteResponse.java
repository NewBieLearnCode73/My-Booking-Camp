package com.example.payment_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {
    private String id;
    private String startLocation;
    private String endLocation;
    private Double distance;
    private Double estimatedTime;
    private List<RouteStopResponse> stops;
}
