package com.example.route_service.repository;

import com.example.route_service.entity.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends MongoRepository<Route, String> {
    Optional<Route> findRouteById(String id);
}
