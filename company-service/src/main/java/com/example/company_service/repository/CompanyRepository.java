package com.example.company_service.repository;

import com.example.company_service.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    boolean existsByOwnerId(String ownerId);
}
