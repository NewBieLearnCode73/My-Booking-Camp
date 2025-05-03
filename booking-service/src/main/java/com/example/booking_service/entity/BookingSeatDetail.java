package com.example.booking_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booking_seat_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSeatDetail {
    @Id
    private String id;

    private String bookingId;

    private String seatCode;

    private double discountPercent = 0.0;

    private double price;
}
