package com.example.notification_service.service;

import com.example.event.NotificationSender;
import com.example.notification_service.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommonNotificationService {
    void saveNotification(NotificationSender notificationSender);
    List<Notification> findByUsernameAndIsReadFalse(String username);
    List<Notification> findAllByUsername(String username);
    void markAsRead(String notificationId);
    void markAllAsRead(String username);
}
