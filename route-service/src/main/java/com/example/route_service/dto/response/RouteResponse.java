package com.example.route_service.dto.response;

import com.example.route_service.entity.Stop;
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
    private List<Stop> stops;
}
