package com.example.notification_service.repository;

import com.example.notification_service.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUsernameAndReadFalse(String userId);
    List<Notification> findAllByUsernameAndReadIsFalse(String userId);
    List<Notification> findAllByUsernameAndReadIsTrue(String userId);
    List<Notification> findAllByUsername(String userId);
}
