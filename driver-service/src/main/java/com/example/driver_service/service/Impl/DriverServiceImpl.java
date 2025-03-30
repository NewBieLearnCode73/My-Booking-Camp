package com.example.driver_service.service.Impl;

import com.example.driver_service.dto.request.DriverRequest;
import com.example.driver_service.dto.response.DriverResponse;
import com.example.driver_service.dto.response.PaginationResponseDTO;
import com.example.driver_service.entity.Driver;
import com.example.driver_service.handle.CustomRunTimeException;
import com.example.driver_service.mapper.DriverMapper;
import com.example.driver_service.repository.DriverRepository;
import com.example.driver_service.service.DriverService;
import com.example.driver_service.utils.DriverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverMapper driverMapper;

    @Override
    public PaginationResponseDTO<DriverResponse> getAllDrivers(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Driver> pagedResult = driverRepository.findAll(paging);

        List<DriverResponse> driverResponseList = pagedResult.stream().map(driverMapper::toDriverResponse).toList();

        return PaginationResponseDTO.<DriverResponse>builder()
                .items(driverResponseList)
                .currentPage(pagedResult.getNumber())
                .totalItems(pagedResult.getTotalElements())
                .totalPages(pagedResult.getTotalPages())
                .pageSize(pagedResult.getSize())
                .build();
    }

    @Override
    @Transactional
    public DriverResponse addDriver(DriverRequest driverRequest) {
        if (driverRepository.existsByContactNumber(driverRequest.getContactNumber())) {
            throw new CustomRunTimeException("Driver with contact number: " + driverRequest.getContactNumber() + " already exists");
        }

        return driverMapper.toDriverResponse(driverRepository.save(driverMapper.toDriver(driverRequest)));
    }

    @Override
    public DriverResponse getDriverById(String id) {
        Driver driver = Optional.ofNullable(driverRepository.findDriverById(id))
                .orElseThrow(() -> new CustomRunTimeException("Driver with id: " + id + " not found"));

        return driverMapper.toDriverResponse(driver);
    }

    @Override
    @Transactional
    public DriverResponse updateDriver(String id, DriverRequest driverRequest) {
        if (driverRepository.existsByContactNumber(driverRequest.getContactNumber())) {
            throw new CustomRunTimeException("Driver with contact number: " + driverRequest.getContactNumber() + " already exists");
        }

        Driver driver = Optional.ofNullable(driverRepository.findDriverById(id))
                .orElseThrow(() -> new CustomRunTimeException("Driver with id: " + id + " not found"));

        driver.setName(driverRequest.getName());
        driver.setLicenseNumber(driverRequest.getLicenseNumber());
        driver.setContactNumber(driverRequest.getContactNumber());
        driver.setAddress(driverRequest.getAddress());
        driver.setStatus(DriverStatus.valueOf(driverRequest.getStatus()));

        return driverMapper.toDriverResponse(driverRepository.save(driver));
    }

    @Override
    public PaginationResponseDTO<DriverResponse> getDriverByDriverStatus(String driverStatus, int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Driver> pagedResult = driverRepository.findAllByStatus(DriverStatus.valueOf(driverStatus), paging);

        for (DriverStatus status : DriverStatus.values()) {
            if (status.name().equals(driverStatus)) {
                List<DriverResponse> driverResponseList = pagedResult.stream().map(driverMapper::toDriverResponse).toList();

                return PaginationResponseDTO.<DriverResponse>builder()
                        .items(driverResponseList)
                        .currentPage(pagedResult.getNumber())
                        .totalItems(pagedResult.getTotalElements())
                        .totalPages(pagedResult.getTotalPages())
                        .pageSize(pagedResult.getSize())
                        .build();
            }
        }

        throw new CustomRunTimeException("Driver status not found");
    }


}
