package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripValidationResponse {
    private String tripId;
    private String type;
    private Boolean isValid;
}
