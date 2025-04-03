package com.example.route_service.service.Impl;

import com.example.event.TripValidationRequest;
import com.example.event.TripValidationResponse;
import com.example.route_service.dto.request.RouteRequest;
import com.example.route_service.dto.response.PaginationResponseDTO;
import com.example.route_service.dto.response.RouteResponse;
import com.example.route_service.entity.Route;
import com.example.route_service.handle.CustomRunTimeException;
import com.example.route_service.mapper.RouteMapper;
import com.example.route_service.repository.RouteRepository;
import com.example.route_service.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public PaginationResponseDTO<RouteResponse> getAllRoutes(int pageNo, int pageSize,String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Route> pagedResult = routeRepository.findAll(paging);

        List<RouteResponse> routeResponses = pagedResult.stream().map(routeMapper::toRouteResponse).toList();

        return PaginationResponseDTO.<RouteResponse>builder()
                .items(routeResponses)
                .currentPage(pagedResult.getNumber())
                .totalItems(pagedResult.getTotalElements())
                .totalPages(pagedResult.getTotalPages())
                .pageSize(pagedResult.getSize())
                .build();
    }

    @Override
    public RouteResponse addRoute(RouteRequest routeRequest) {
        Route myRoute = routeRepository.save(routeMapper.toRoute(routeRequest));

        return routeMapper.toRouteResponse(myRoute);
    }

    @Override
    public RouteResponse getRouteById(String id) {
        Route route = routeRepository.findRouteById(id).orElse(null);

        if (route == null) {
            throw new CustomRunTimeException("Route not found");
        }
        else {
            return routeMapper.toRouteResponse(route);
        }
    }

    @Override
    public RouteResponse updateRouteById(String id, RouteRequest routeRequest) {
        Route route = routeRepository.findRouteById(id).orElse(null);

        if (route == null) {
            throw new CustomRunTimeException("Route not found");
        }
        else {
            route.setStartLocation(routeRequest.getStartLocation());
            route.setEndLocation(routeRequest.getEndLocation());
            route.setDistance(routeRequest.getDistance());
            route.setEstimatedTime(routeRequest.getEstimatedTime());
            route.setStops(routeRequest.getStops());

            Route updatedRoute = routeRepository.save(route);

            return routeMapper.toRouteResponse(updatedRoute);
        }
    }


    @Override
    public void deleteRoute(String id) {
        Route route = routeRepository.findRouteById(id).orElse(null);

        if (route == null) {
            throw new CustomRunTimeException("Route not found");
        }
        else {
            routeRepository.delete(route);
        }
    }

    @Override
    @KafkaListener(topics = "validate-route", groupId = "route-service")
    public void validateRoute(TripValidationRequest tripValidationRequest) {
        boolean isRouteValid = routeRepository.existsById(tripValidationRequest.getRouteId());

        log.info("Route validation result: {}", isRouteValid);

        TripValidationResponse tripValidationResponse = new TripValidationResponse();

        tripValidationResponse.setTripId(tripValidationRequest.getTripId());
        tripValidationResponse.setType("route");
        tripValidationResponse.setIsValid(isRouteValid);

        kafkaTemplate.send("trip-validation-response", tripValidationResponse);
    }
}
