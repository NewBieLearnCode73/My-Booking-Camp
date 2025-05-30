package com.example.coach_service.dto.response;

import com.example.coach_service.entity.SeatLayout;
import com.example.coach_service.utils.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachTypeResponse {
    private String name;
    private Type type;
    private SeatLayout seatLayout;
}
