package com.example.coach_service.entity;

import com.example.coach_service.utils.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "coachType")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachType {
    @Id
    private String id;
    private String name;
    private Type type;

    private SeatLayout seatLayout;

    public int getTotalSeats(){
        return seatLayout != null ? seatLayout.calculateTotalSeats() : 0;
    }
}
