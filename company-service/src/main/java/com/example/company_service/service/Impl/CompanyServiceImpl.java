package com.example.company_service.service.Impl;

import com.example.company_service.dto.request.CompanyRequest;
import com.example.company_service.dto.response.CompanyExistedResponse;
import com.example.company_service.dto.response.CompanyResponse;
import com.example.company_service.entity.Company;
import com.example.company_service.handle.CustomRunTimeException;
import com.example.company_service.mapper.CompanyMapper;
import com.example.company_service.repository.CompanyRepository;
import com.example.company_service.repository.httpClient.AuthClient;
import com.example.company_service.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private AuthClient authClient;

    @Override
    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        if(!authClient.isOwnerExist(companyRequest.getOwnerId()).isExisted()){
            throw new CustomRunTimeException("Owner with " + companyRequest.getOwnerId() + " not found");
        }

        boolean existingCompany = companyRepository.existsByOwnerId(companyRequest.getOwnerId());

        if (existingCompany) {
            throw new CustomRunTimeException("Owner with " + companyRequest.getOwnerId() + " already has a company");
        }

        Company company = companyMapper.mapToCompany(companyRequest);

        return companyMapper.mapToCompanyResponse(companyRepository.save(company));
    }

    @Override
    public CompanyResponse getCompanyById(String id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CustomRunTimeException("Company with " + id + " not found"));
        return companyMapper.mapToCompanyResponse(company);
    }

    @Override
    public CompanyExistedResponse isCompanyExist(String id) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company != null) {
            return CompanyExistedResponse.builder()
                    .existed(true)
                    .message("Company with " + id + " already exists")
                    .build();
        } else {
            return CompanyExistedResponse.builder()
                    .existed(false)
                    .message("Company with " + id + " does not exist")
                    .build();
        }
    }

    @Override
    public CompanyResponse updateCompany(String id, CompanyRequest companyRequest) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CustomRunTimeException("Company with " + id + " not found"));
        company.setCompanyName(companyRequest.getCompanyName());
        company.setAddress(companyRequest.getAddress());
        company.setTaxCode(companyRequest.getTaxCode());
        company.setOwnerId(companyRequest.getOwnerId());

        return companyMapper.mapToCompanyResponse(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(String id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CustomRunTimeException("Company with " + id + " not found"));
        companyRepository.delete(company);
    }

    @Override
    public CompanyResponse getCompanyByOwnerId(String ownerId) {
        return null;
    }
}
