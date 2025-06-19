package com.example.auth_service.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public interface MyJwtRedisService {
    public void saveJwtToRedis(String userId, String token, long ttl);
    public void saveRefreshTokenToRedis(String refreshToken, long ttl);
    public boolean isJwtValid(String token);
    public void cleanExpiredToken(String userId);
    public Map<String, String> getTokenInfo(String token);
    public void putTokenToBlackList(String token);
    public boolean isTokenBlackListed(String token);
    public void logoutAndBlacklistToken(String token);
    public void deleteRefreshToken(String token);
    public boolean hasRefreshToken(String token);
}
