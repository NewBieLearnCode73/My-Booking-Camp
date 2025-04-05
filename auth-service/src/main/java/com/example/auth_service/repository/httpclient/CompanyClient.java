package com.example.auth_service.repository.httpclient;


import com.example.auth_service.dto.response.CompanyExistedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service", url = "http://localhost:8097/company/is-existed-with-id")
public interface CompanyClient {
    @GetMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyExistedResponse isCompanyExist(@PathVariable String id);
}
