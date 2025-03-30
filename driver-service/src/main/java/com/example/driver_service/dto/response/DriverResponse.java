package com.example.driver_service.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponse {
        private String id;
        private String name;
        private String licenseNumber;
        private String contactNumber;
        private String address;
        private String status;
}
