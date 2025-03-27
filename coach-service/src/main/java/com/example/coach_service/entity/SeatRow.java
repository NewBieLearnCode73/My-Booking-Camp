package com.example.coach_service.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRow {

    @NotEmpty(message = "Please provide a row")
    private String row;

    @NotEmpty(message = "Please provide seats")
    private List<String> seats;
}
