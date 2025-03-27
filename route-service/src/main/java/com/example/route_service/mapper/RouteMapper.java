package com.example.route_service.mapper;

import com.example.route_service.dto.request.RouteRequest;
import com.example.route_service.dto.response.RouteResponse;
import com.example.route_service.entity.Route;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    Route toRoute(RouteRequest routeRequest);
    RouteResponse toRouteResponse(Route route);
}
