package com.example.trip_service.repository.httpClients;

import com.example.trip_service.config.FeignClientConfig;
import com.example.trip_service.dto.response.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service", url = "http://localhost:8097/company", configuration = FeignClientConfig.class)
public interface CompanyRepository {
    @GetMapping(value = "/{id}",
            consumes = "application/json",
            produces = "application/json")
    CompanyResponse getCompanyById(@PathVariable String id);
}
