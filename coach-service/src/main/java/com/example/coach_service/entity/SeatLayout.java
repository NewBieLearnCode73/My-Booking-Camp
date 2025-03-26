package com.example.coach_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatLayout {
    private List<SeatRow> seatRows;

    public int calculateTotalSeats(){
        if(seatRows == null || seatRows.isEmpty()){
            return 0;
        }

        return seatRows.stream()
                .mapToInt(row -> row.getSeats() != null ? row.getSeats().size() : 0)
                .sum();
    }
}
