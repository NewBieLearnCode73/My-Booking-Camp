package com.example.driver_service.service;

import com.example.driver_service.dto.request.DriverRequest;
import com.example.driver_service.dto.response.DriverResponse;
import com.example.driver_service.dto.response.PaginationResponseDTO;
import com.example.event.TripValidationRequest;
import org.springframework.stereotype.Service;

@Service
public interface DriverService {
    PaginationResponseDTO<DriverResponse> getAllDrivers(int pageNo, int pageSize, String sortBy);
    DriverResponse addDriver(DriverRequest driverRequest);
    DriverResponse getDriverById(String id);
    DriverResponse updateDriver(String id, DriverRequest driverRequest);
    PaginationResponseDTO<DriverResponse> getDriverByDriverStatus(String driverStatus, int pageNo, int pageSize, String sortBy);
    void validateDriver(TripValidationRequest tripValidationRequest);
}
