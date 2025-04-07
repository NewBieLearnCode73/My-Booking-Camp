package com.example.company_service.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequest {
    @NotEmpty(message = "Company name is required")
    private String companyName;

    @NotEmpty(message = "Owner ID is required")
    private String ownerId;

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "Tax code is required")
    private String taxCode;
}
