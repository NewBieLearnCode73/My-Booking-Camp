package com.example.company_service.mapper;

import com.example.company_service.dto.request.CompanyRequest;
import com.example.company_service.dto.response.CompanyResponse;
import com.example.company_service.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company mapToCompany(CompanyRequest companyRequest);
    CompanyResponse mapToCompanyResponse(Company company);
}
