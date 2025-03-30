package com.example.driver_service.dto.request;

import com.example.driver_service.utils.DriverStatus;
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
public class DriverRequest {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "License Number is required")
    private String licenseNumber;

    @NotEmpty(message = "Contact Number is required")
    @Size(min = 10, max = 10, message = "Contact Number should be 10 digits")
    private String contactNumber;

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "Status is required")
    @Pattern(regexp = "^(AVAILABLE|UNAVAILABLE|ON_TRIP)$", message = "Status should be either AVAILABLE, UNAVAILABLE or ON_TRIP")
    private String status = DriverStatus.AVAILABLE.name();
}
