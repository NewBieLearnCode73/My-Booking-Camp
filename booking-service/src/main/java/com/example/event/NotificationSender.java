package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSender {
    private String username;
    private String title;
    private String message;
    private boolean read;
    private LocalDateTime timestamp;

    public NotificationSender(String username, String message){
        this.username = username;
        this.message = message;
        this.read = false;
        this.timestamp = LocalDateTime.now();
    }
}
