package com.example.company_service.service;

import com.example.company_service.dto.request.CompanyRequest;
import com.example.company_service.dto.response.CompanyExistedResponse;
import com.example.company_service.dto.response.CompanyResponse;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
    CompanyResponse createCompany(CompanyRequest companyRequest);

    CompanyResponse getCompanyById(String id);

    CompanyExistedResponse isCompanyExist(String id);

    CompanyResponse updateCompany(String id, CompanyRequest companyRequest);

    void deleteCompany(String id);

    CompanyResponse getCompanyByOwnerId(String ownerId);
}