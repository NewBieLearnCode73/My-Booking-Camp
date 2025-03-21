package com.example.notification_service.service;

import com.example.notification_service.dto.request.SendEmailRequest;
import com.example.notification_service.dto.response.EmailResponse;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    EmailResponse sendMail(SendEmailRequest sendEmailRequest);
}
