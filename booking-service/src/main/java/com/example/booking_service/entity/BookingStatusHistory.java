package com.example.booking_service.entity;

import com.example.booking_service.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "booking_status_history")
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookingStatusHistory {
    @Id
    private String id;

    private String bookingId;

    private Status oldStatus;
    private Status newStatus;

    private String updatedByUsername;

    private LocalDate updateDate;
    private LocalTime updateTime;
}
