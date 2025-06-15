package com.example.api_gateway.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface MyRedisService {
    public boolean isJwtValid(String token);
    public Map<String, String> getTokenInfo(String token);
    public void putTokenToBlackList(String token);
    public boolean isTokenBlackListed(String token);
}
