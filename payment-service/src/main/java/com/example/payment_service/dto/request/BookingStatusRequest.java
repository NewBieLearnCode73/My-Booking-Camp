package com.example.payment_service.dto.request;

import com.example.payment_service.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingStatusRequest {
    private String bookingId;
    private Status status;
}
