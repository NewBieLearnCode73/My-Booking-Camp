package com.example.api_gateway.service.Impl;

import com.example.api_gateway.service.MyRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MyRedisServiceImpl implements MyRedisService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean isJwtValid(String token) {
        String tokenKey = "jwt_cache:" + token;
        if (!redisTemplate.hasKey(tokenKey)) {
            return false;
        }

        return !isTokenBlackListed(token);
    }

    @Override
    public Map<String, String> getTokenInfo(String token) {
        String tokenKey = "jwt_cache:" + token;
        Map<Object, Object> tokenInfo = redisTemplate.opsForHash().entries(tokenKey);
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry : tokenInfo.entrySet()) {
            result.put((String) entry.getKey(), (String) entry.getValue());
        }
        return result;
    }

    @Override
    public void putTokenToBlackList(String token) {
        String tokenKey = "blacklist:" + token;
        redisTemplate.opsForValue().set(tokenKey, "blacklisted", -1, TimeUnit.DAYS);
    }

    @Override
    public boolean isTokenBlackListed(String token) {
        String tokenKey = "blacklist:" + token;
        return redisTemplate.hasKey(tokenKey);
    }
}
