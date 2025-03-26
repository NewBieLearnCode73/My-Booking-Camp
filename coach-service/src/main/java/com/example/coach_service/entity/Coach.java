package com.example.coach_service.entity;

import com.example.coach_service.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coach")
public class Coach {
    @Id
    private String id;

    private String licensePlate;

    private Status status;

    private String description;

    private String coachTypeId;
}
