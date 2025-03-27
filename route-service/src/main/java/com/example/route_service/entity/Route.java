package com.example.route_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "route")
public class Route {
    @Id
    private String id;
    private String startLocation;
    private String endLocation;
    private Double distance;
    private Double estimatedTime;
    private List<Stop> stops;

}
