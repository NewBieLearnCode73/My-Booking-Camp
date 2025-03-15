package com.example.auth_service.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface MyJwtRedisService {

    // tll: miliseconds from now to expire
    public void saveJwtToRedis(String userId, String token, long ttl);
    public boolean isJwtValid(String userId, String token);
    public ArrayList<Object> getUserToken(String userId);
    public void cleanExpiredToken(String userId);
}
