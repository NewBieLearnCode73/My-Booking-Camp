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
    @Column(name = "company_name")
    private String companyName;

    @NotEmpty(message = "Owner ID is required")
    @Column(name = "owner_id")
    private String ownerId;

    @NotEmpty(message = "Address is required")
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "Tax code is required")
    @Column(name = "tax_code")
    private String taxCode;
}
