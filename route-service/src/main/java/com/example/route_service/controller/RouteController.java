package com.example.route_service.controller;

import com.example.route_service.custom.RequireRole;
import com.example.route_service.dto.request.RouteRequest;
import com.example.route_service.service.RouteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/route")
    public ResponseEntity<?> getAllRoutes(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(routeService.getAllRoutes(pageNo, pageSize, sortBy));
    }

    @GetMapping("/route/{id}")
    public ResponseEntity<?> getRouteById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(routeService.getRouteById(id));
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping("/route")
    public ResponseEntity<?> addRoute(@RequestBody @Valid RouteRequest routeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(routeService.addRoute(routeRequest));
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER"})
    @PutMapping("/route/{id}")
    public ResponseEntity<?> updateRouteById(@PathVariable String id, @RequestBody @Valid RouteRequest routeRequest) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(routeService.updateRouteById(id, routeRequest));
    }
 }
