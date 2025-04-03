package com.example.route_service.service;

import com.example.event.TripValidationRequest;
import com.example.route_service.dto.request.RouteRequest;
import com.example.route_service.dto.response.PaginationResponseDTO;
import com.example.route_service.dto.response.RouteResponse;
import com.example.route_service.entity.Route;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteService {
    PaginationResponseDTO<RouteResponse> getAllRoutes(int pageNo, int pageSize, String sortBy);
    RouteResponse addRoute(RouteRequest routeRequest);
    RouteResponse getRouteById(String id);
    RouteResponse updateRouteById(String id,RouteRequest routeRequest);
    void deleteRoute(String id);
    void validateRoute(TripValidationRequest tripValidationRequest);
}