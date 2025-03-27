package com.example.route_service.dto.request;

import com.example.route_service.entity.Stop;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequest {

    @NotEmpty(message = "Start location is required")
    private String startLocation;

    @NotEmpty(message = "End location is required")
    private String endLocation;

    @NotNull(message = "Distance is required")
    @Min(value = 1, message = "Distance must be greater than 0")
    private Double distance;

    @NotNull(message = "Estimated time is required")
    @Min(value = 1, message = "Estimated time must be greater than 0")
    private Double estimatedTime;

    @NotNull(message = "Stops list cannot be null")
    private List<@NotNull(message = "Stop cannot be null") Stop> stops;
}
