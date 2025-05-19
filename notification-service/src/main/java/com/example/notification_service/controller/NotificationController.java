package com.example.notification_service.controller;

import com.example.event.NotificationEvent;
import com.example.event.NotificationSender;
import com.example.notification_service.custom.RequireRole;
import com.example.notification_service.dto.request.Recipient;
import com.example.notification_service.dto.request.SendEmailRequest;
import com.example.notification_service.entity.Notification;
import com.example.notification_service.service.CommonNotificationService;
import com.example.notification_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private CommonNotificationService  commonNotificationService;

    @KafkaListener(topics = "user-registered")
    public void listenUserRegistered(NotificationEvent notificationEvent) {
        emailService.sendMail(SendEmailRequest.builder()
                .to(Recipient.builder().email(notificationEvent.getEmail()).name(notificationEvent.getName()).build())
                .subject(notificationEvent.getSubject())
                .htmlContent(notificationEvent.getHtmlContent())
                .build());
    }

    @KafkaListener(topics = "send-notification")
    public void listenPushNotification(NotificationSender notificationSender) {
        commonNotificationService.saveNotification(notificationSender);
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF", "ROLE_CUSTOMER"})
    @GetMapping("/notification/unread/{userId}")
    public List<Notification> getUnreadNotification(@PathVariable String userId) {
        return commonNotificationService.findByUsernameAndIsReadFalse(userId);
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF", "ROLE_CUSTOMER"})
    @GetMapping("/notification/{username}")
    public List<Notification> getAllNotification(@PathVariable String username) {
        return commonNotificationService.findAllByUsername(username);
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF", "ROLE_CUSTOMER"})
    @GetMapping("/notification/{id}/read")
    public void markNotificationAsRead(@PathVariable String id) {
        commonNotificationService.markAsRead(id);
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF", "ROLE_CUSTOMER"})
    @GetMapping("/notification/username/{username}/read")
    public void markAllNotificationAsRead(@PathVariable String username) {
        commonNotificationService.markAllAsRead(username);
    }
}
