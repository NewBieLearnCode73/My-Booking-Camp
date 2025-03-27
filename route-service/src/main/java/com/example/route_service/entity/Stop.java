package com.example.route_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    private String name;
    private Double arrival_time_offset_minutes;
}
