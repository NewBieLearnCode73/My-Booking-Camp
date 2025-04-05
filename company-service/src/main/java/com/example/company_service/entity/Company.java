package com.example.company_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "owner_id" , nullable = false)
    private String ownerId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "tax_code", nullable = false)
    private String taxCode;
}
