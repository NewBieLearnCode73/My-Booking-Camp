package com.example.notification_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private Sender sender;
    private List<Recipient> to;
    private String subject;
    private String htmlContent;
}
