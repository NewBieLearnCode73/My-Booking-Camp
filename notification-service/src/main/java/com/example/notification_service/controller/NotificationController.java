package com.example.notification_service.controller;

import com.example.event.NotificationEvent;
import com.example.notification_service.dto.request.Recipient;
import com.example.notification_service.dto.request.SendEmailRequest;
import com.example.notification_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "user-registered")
    public void listenUserRegistered(NotificationEvent notificationEvent) {
        emailService.sendMail(SendEmailRequest.builder()
                .to(Recipient.builder().email(notificationEvent.getEmail()).name(notificationEvent.getName()).build())
                .subject(notificationEvent.getSubject())
                .htmlContent(notificationEvent.getHtmlContent())
                .build());
    }
}
