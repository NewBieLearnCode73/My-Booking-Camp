package com.example.trip_service.utils;

public enum TripStatus {
    SCHEDULED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
    PENDING // When created and waiting for kafka to send the message
}