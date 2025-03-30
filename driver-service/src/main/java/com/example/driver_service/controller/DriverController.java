package com.example.driver_service.controller;

import com.example.driver_service.dto.request.DriverRequest;
import com.example.driver_service.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/driver")
    public ResponseEntity<?> getAllDrivers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(driverService.getAllDrivers(pageNo, pageSize, sortBy));
    }

    @GetMapping("/driver/status/{driverStatus}")
    public ResponseEntity<?> getDriverByDriverStatus(@PathVariable String driverStatus,
                                                     @RequestParam(defaultValue = "0") int pageNo,
                                                     @RequestParam(defaultValue = "10") int pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(driverService.getDriverByDriverStatus(driverStatus, pageNo, pageSize, sortBy));
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<?> getDriverById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(driverService.getDriverById(id));
    }

    @PutMapping("/driver/{id}")
    public ResponseEntity<?> updateDriverById(@PathVariable String id, @Valid @RequestBody DriverRequest driverRequest) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(driverService.updateDriver(id, driverRequest));
    }

    @PostMapping("/driver")
    public ResponseEntity<?> addDriver(@RequestBody @Valid DriverRequest driverRequest) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(driverService.addDriver(driverRequest));
    }
}
