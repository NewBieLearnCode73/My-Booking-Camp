package com.example.auth_service.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, String> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().iterator().next().toString());

        return Jwts
                .builder()
                .signWith(getSigningKey())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(Constants.VALIDITY)))
                .subject(userDetails.getUsername())
                .claims(claims).compact();
    }

    public String generateRefreshToken(UserDetails userDetails){
        return Jwts.builder()
                .signWith(getSigningKey())
                .expiration(Date.from(Instant.now().plusMillis(Constants.REFRESH)))
                .issuedAt(Date.from(Instant.now()))
                .subject(userDetails.getUsername())
                .compact();
    }

    public Claims claims(String token){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    public String extractUsername(String token){
        return claims(token).getSubject();
    }

    public Date extractExpiration(String token){
        return claims(token).getExpiration();
    }

    public String getRole(String token){
        return (String) claims(token).get("role");
    }

    // Check if the token is valid (The token is not expired, the signature is valid, the token is not empty)
    public boolean isTokenValid(String token){
        try {
            return claims(token).getExpiration().after(Date.from(Instant.now()));
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Token is expired!");
        } catch (MalformedJwtException ex) {
            throw new JwtException("Token is invalid with structure!");
        } catch (SignatureException ex) {
            throw new JwtException("Token is invalid with signature!");
        } catch (IllegalArgumentException ex) {
            throw new JwtException("Token is empty or not existed!");
        } catch (JwtException ex) {
            throw new JwtException("Token is invalid!");
        }
    }
}
