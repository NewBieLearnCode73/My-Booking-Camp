package com.example.coach_service.dto.request;

import com.example.coach_service.entity.SeatLayout;
import com.example.coach_service.utils.Type;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachTypeRequest {
    @NotEmpty(message = "Please provide a name")
    private String name;

    @Pattern(regexp = "^NORMAL|MEDIUM|LUXURY$", message = "Type must be one in NORMAL, MEDIUM or LUXURY")
    private String type;

    @Valid
    @NotNull(message = "Please provide a seat layout")
    private SeatLayout seatLayout;
}
