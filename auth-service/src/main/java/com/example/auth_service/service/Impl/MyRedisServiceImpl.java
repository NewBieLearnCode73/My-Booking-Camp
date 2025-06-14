package com.example.auth_service.service.Impl;

import com.example.auth_service.service.MyJwtRedisService;
import com.example.auth_service.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MyRedisServiceImpl implements MyJwtRedisService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    private final int MAX_TOKENS = 5;

    @Override
    public void saveJwtToRedis(String userId, String token, long ttl) {
        cleanExpiredToken(userId);

        Map<String, String> jwtCacheInfo = new HashMap<>();
        String username = jwtUtils.extractUsername(token);
        String role = jwtUtils.getRole(token);
        jwtCacheInfo.put("userId", userId);
        jwtCacheInfo.put("username", username);
        jwtCacheInfo.put("role", role);

        String  tokenKey = "jwt_cache:" + token;
        String tokenSortedSet = "jwt_sorted_set:" + userId;

        Long tokenCount = redisTemplate.opsForZSet().zCard(tokenSortedSet);
        if (tokenCount >= MAX_TOKENS) {
            Set<Object> oldestTokens = redisTemplate.opsForZSet().range(tokenSortedSet, 0, 0);
            if (!oldestTokens.isEmpty()) {
                String oldestToken = (String) oldestTokens.iterator().next();
                redisTemplate.opsForZSet().remove(tokenSortedSet, oldestToken);
                redisTemplate.delete("jwt_cache:" + oldestToken);
                log.info("HEEE", oldestToken);
                putTokenToBlackList(oldestToken);
                log.info("Removed oldest token: {}", oldestToken);
            }
        }

        redisTemplate.opsForHash().putAll(tokenKey, jwtCacheInfo);
        redisTemplate.expire(tokenKey, ttl, TimeUnit.MILLISECONDS);

        long expireAt = System.currentTimeMillis() + ttl;
        redisTemplate.opsForZSet().add(tokenSortedSet, token, expireAt);
    }

    @Override
    public boolean isJwtValid(String token) {
        // Check trong record đầu tiên của redis
        String tokenKey = "jwt_cache:" + token;
        if (!redisTemplate.hasKey(tokenKey)) {
            return false; // Token không tồn tại trong Redis
        }

        // Kiểm tra xem token có bị blacklist hay không
        return !isTokenBlackListed(token);
    }


    // clean all expired token in the sorted set
    @Override
    public void cleanExpiredToken(String userId) {
        String tokenSortedSet = "jwt_sorted_set:" + userId;
        Set<Object> expiredTokens = redisTemplate.opsForZSet().rangeByScore(tokenSortedSet, 0, System.currentTimeMillis());
        if (!expiredTokens.isEmpty()) {
            for (Object token : expiredTokens) {
                redisTemplate.opsForZSet().remove(tokenSortedSet, token);
                redisTemplate.delete("jwt_cache:" + token);
                putTokenToBlackList((String) token);
            }
        }
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
        // Đặt token vào blacklist với TTL là vĩnh viễn
        redisTemplate.opsForValue().set(tokenKey, "blacklisted");
    }

    @Override
    public boolean isTokenBlackListed(String token) {
        String tokenKey = "blacklist:" + token;
        return redisTemplate.hasKey(tokenKey);
    }
}
