package com.example.auth_service.service.Impl;

import com.example.auth_service.service.MyJwtRedisService;
import com.example.auth_service.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class MyRedisServiceImpl implements MyJwtRedisService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    private final int MAX_TOKENS = 5;

    @Override
    public void saveJwtToRedis(String userId, String token, long ttl) {
        String tokenKey = "jwt:" + userId + ":" + token;
        String tokenSortedSet = "jwt_sorted_set:" + userId;

        cleanExpiredToken(userId);

        Long tokenCount = redisTemplate.opsForZSet().zCard(tokenSortedSet);
        if (tokenCount >= MAX_TOKENS) {
            // Xóa token cũ nhất
            Set<Object> oldestTokens = redisTemplate.opsForZSet().range(tokenSortedSet, 0, 0);
            if (!oldestTokens.isEmpty()) {
                String oldestToken = (String) oldestTokens.iterator().next();
                redisTemplate.opsForZSet().remove(tokenSortedSet, oldestToken);
                redisTemplate.delete("jwt:" + userId + ":" + oldestToken);
            }
        }

        redisTemplate.opsForValue().set(tokenKey, token, ttl, TimeUnit.MILLISECONDS);

        long expireAt = System.currentTimeMillis() + ttl;
        redisTemplate.opsForZSet().add(tokenSortedSet, token, expireAt);
    }

    @Override
    public boolean isJwtValid(String userId, String token) {
        String key = "jwt:" + userId + ":" + token;
        return redisTemplate.hasKey(key);
    }

    @Override
    public ArrayList<Object> getUserToken(String userId) {
        String sortedSetKey = "jwt_sorted_set:" + userId;
        long now = System.currentTimeMillis();

        Set<Object> validTokens = redisTemplate.opsForZSet().rangeByScore(sortedSetKey, now, Double.MAX_VALUE);
        return new ArrayList<>(validTokens);
    }


    // clean all expired token in the sorted set
    @Override
    public void cleanExpiredToken(String userId) {
        String tokenSortedSet = "jwt_sorted_set:" + userId;
        long now = System.currentTimeMillis();

        Set<Object> expiredTokens = redisTemplate.opsForZSet().rangeByScore(tokenSortedSet, 0, now);
        if (!expiredTokens.isEmpty()) {
            for (Object token : expiredTokens) {
                redisTemplate.opsForZSet().remove(tokenSortedSet, token);
                redisTemplate.delete("jwt:" + userId + ":" + token);
            }
        }
    }
}
