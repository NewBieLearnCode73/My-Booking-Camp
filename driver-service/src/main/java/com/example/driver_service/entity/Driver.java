package com.example.driver_service.entity;

import com.example.driver_service.utils.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "driver")
public class Driver {
    @Id
    private String id;
    private String name;
    private String licenseNumber;
    private String contactNumber;
    private String address;
    private DriverStatus status;
}
