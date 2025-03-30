package com.example.driver_service.mapper;

import com.example.driver_service.dto.request.DriverRequest;
import com.example.driver_service.dto.response.DriverResponse;
import com.example.driver_service.entity.Driver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    Driver toDriver(DriverRequest driverRequest);
    DriverResponse toDriverResponse(Driver driver);
}
