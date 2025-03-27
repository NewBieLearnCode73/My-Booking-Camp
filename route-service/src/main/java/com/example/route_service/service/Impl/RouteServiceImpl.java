package com.example.route_service.service.Impl;

import com.example.route_service.dto.request.RouteRequest;
import com.example.route_service.dto.response.RouteResponse;
import com.example.route_service.entity.Route;
import com.example.route_service.handle.CustomRunTimeException;
import com.example.route_service.mapper.RouteMapper;
import com.example.route_service.repository.RouteRepository;
import com.example.route_service.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteMapper routeMapper;

    @Override
    public List<RouteResponse> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();

        return routes.stream().map(routeMapper::toRouteResponse).toList();
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
}
