package com.example.payment_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "booking_id", nullable = false)
    private String bookingId;

    @Column(name = "staff_username", nullable = false)
    private String staffUsername;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "payment_time", nullable = false)
    private LocalTime paymentTime;
}
