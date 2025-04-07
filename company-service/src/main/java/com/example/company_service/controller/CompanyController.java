package com.example.company_service.controller;

import com.example.company_service.dto.request.CompanyRequest;
import com.example.company_service.handle.AuthorizationException;
import com.example.company_service.handle.CustomRunTimeException;
import com.example.company_service.service.CompanyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/company")
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyRequest companyRequest, @RequestHeader ("Authorization") String token) {
        log.info("Received token: {}", token);

        if(token == null || token.isEmpty()) {
            throw new AuthorizationException("Authorization token is missing");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(companyRequest, token));
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getCompanyById(@Valid @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }

    @GetMapping("/company/is-existed-with-id/{id}")
    public ResponseEntity<?> isCompanyExist(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.isCompanyExist(id));
    }

    @GetMapping("/company/getByOwnerId/{ownerId}")
    public ResponseEntity<?> getCompanyByOwnerId(@PathVariable String ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyByOwnerId(ownerId));
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<?> updateCompany(@Valid @PathVariable String id, @Valid @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateCompany(id, companyRequest));
    }
}
