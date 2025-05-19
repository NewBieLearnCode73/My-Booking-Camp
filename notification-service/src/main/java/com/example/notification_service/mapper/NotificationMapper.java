package com.example.notification_service.mapper;

import com.example.event.NotificationSender;
import com.example.notification_service.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(NotificationSender notificationSender);
}
