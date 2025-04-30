package com.example.booking_service.entity;


import com.example.booking_service.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "booking")
public class Booking {
    @Id
    private String id;

   private String tripId;

   private String userUsername;

   private List<String> bookedSeats;

   private String createdByUserUsername;

   private String createByEmployeeUsername;

   private String note;

   private Status status;

   private String phoneNumber;

   private LocalDate createdDate;

   private LocalTime createdTime;
}
