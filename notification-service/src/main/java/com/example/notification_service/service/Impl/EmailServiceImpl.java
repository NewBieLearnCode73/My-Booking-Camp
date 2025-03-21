package com.example.notification_service.service.Impl;

import com.example.notification_service.dto.request.EmailRequest;
import com.example.notification_service.dto.request.SendEmailRequest;
import com.example.notification_service.dto.request.Sender;
import com.example.notification_service.dto.response.EmailResponse;
import com.example.notification_service.handle.CustomRunTimeException;
import com.example.notification_service.repository.httpClient.EmailClient;
import com.example.notification_service.service.EmailService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailClient emailClient;

    @Value("${apiKey}")
    private String apiKey;

    @Override
    public EmailResponse sendMail(SendEmailRequest sendEmailRequest) {
        EmailRequest emailRequest = EmailRequest
                .builder()
                .sender(Sender.builder().email("ndchieu73@gmail.com").name("Chieu").build())
                .to(List.of(sendEmailRequest.getTo()))
                .subject(sendEmailRequest.getSubject())
                .htmlContent(sendEmailRequest.getHtmlContent())
                .build();
        
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        }
        catch (FeignException.FeignClientException e) {
            e.printStackTrace();
            throw new CustomRunTimeException("Email not sent");
        }
    }

}
