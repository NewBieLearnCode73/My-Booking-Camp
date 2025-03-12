package com.example.auth_service.controller;

import com.example.auth_service.entity.LoginForm;
import com.example.auth_service.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/auth/login")
    public ResponseEntity<?> authAndGetToken(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password())
        );


        if(authentication.isAuthenticated()){
            Map<String, String> token = new HashMap<>();

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.username());
            String accessToken = jwtUtils.generateToken(userDetails);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails);

            token.put("access_token", accessToken);
            token.put("refresh_token", refreshToken);

            return ResponseEntity.status(HttpStatus.OK.value()).body(token);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(Map.of("message", "Unauthorized"));
        }
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> token){
        String refreshToken = token.get("refresh_token");
        if(jwtUtils.isTokenValid(refreshToken)){
            String username = jwtUtils.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            String newAccessToken = jwtUtils.generateToken(userDetails);
            String newRefreshToken = jwtUtils.generateRefreshToken(userDetails);

            Map<String, String> newToken = new HashMap<>();
            newToken.put("access_token", newAccessToken);
            newToken.put("refresh_token", newRefreshToken);

            return ResponseEntity.status(HttpStatus.OK.value()).body(newToken);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(Map.of("message", "Invalid token"));
        }
    }
}
