package com.example.notification_service.controller;

import com.example.notification_service.custom.RequireRole;
import com.example.notification_service.dto.request.SendEmailRequest;
import com.example.notification_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailControler {
    @Autowired
    private EmailService emailService;

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF"})
    @PostMapping("/email/send")
    ResponseEntity<?> sendEmail(@RequestBody SendEmailRequest sendEmailRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.sendMail(sendEmailRequest));
    }

}
