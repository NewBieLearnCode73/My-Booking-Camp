package com.example.payment_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private String id;
    private String tripId;
    private String userUsername;
    private List<String> bookedSeats;
    private String createdByUserUsername;
    private String createByEmployeeUsername;

    private String note;
    private String status;
    private String phoneNumber;

    private String createdDate;
    private String createdTime;
    private String confirmedDate;
    private String confirmedTime;
}
