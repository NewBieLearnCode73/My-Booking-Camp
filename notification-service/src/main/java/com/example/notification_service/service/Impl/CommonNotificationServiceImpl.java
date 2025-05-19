package com.example.notification_service.service.Impl;

import com.example.event.NotificationSender;
import com.example.notification_service.entity.Notification;
import com.example.notification_service.mapper.NotificationMapper;
import com.example.notification_service.repository.NotificationRepository;
import com.example.notification_service.service.CommonNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonNotificationServiceImpl implements CommonNotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public void saveNotification(NotificationSender notificationSender) {
        notificationRepository.save(notificationMapper.toNotification(notificationSender));
    }

    @Override
    public List<Notification> findByUsernameAndIsReadFalse(String username) {
        return notificationRepository.findByUsernameAndReadFalse(username);
    }

    @Override
    public List<Notification> findAllByUsername(String username) {
        return notificationRepository.findAllByUsername(username);
    }

    @Override
    public void markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(String username) {
        List<Notification> notifications = notificationRepository.findAllByUsernameAndReadIsFalse(username);
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
    }
}