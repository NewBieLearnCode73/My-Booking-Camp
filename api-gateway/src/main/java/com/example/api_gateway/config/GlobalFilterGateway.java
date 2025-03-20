package com.example.api_gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class GlobalFilterGateway implements GlobalFilter, Ordered {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final List<String> allowedUrls = Arrays.asList(
            "/auth/login",
            "/auth/register",
            "/auth/refresh-token",
            "/auth/validate-token"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        if (allowedUrls.contains(path)) {
            return chain.filter(exchange); // next filter
        }

        // Get request from server
        ServerHttpRequest request = exchange.getRequest();

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            try {
                return onError(exchange.getResponse(), "401", "Authorization header is missing");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (token.isEmpty()) {
                try {
                    return onError(exchange.getResponse(), "401", "Token is empty");
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/auth/validate-token")
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(req -> {

                    log.info("User: {} Role: {}", req.get("user_username"), req.get("user_role"));

                    // Create a new request with additional headers
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("X-Auth-Username", req.get("user_username").toString())
                            .header("X-Auth-Roles", req.get("user_role").toString())
                            .build();

                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();


                    return chain.filter(modifiedExchange);
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> onError(ServerHttpResponse response, String statusCode, String message) throws JsonProcessingException {
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        Map<String, String> myResponse = new HashMap<>();
        myResponse.put("status", statusCode);
        myResponse.put("message", message);
        myResponse.put("timeStamp", String.valueOf(System.currentTimeMillis()));

        byte[] bytes = new ObjectMapper().writeValueAsBytes(myResponse);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }
}