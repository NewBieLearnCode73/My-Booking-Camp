package com.example.auth_service.controller;

import com.example.auth_service.entity.LoginForm;
import com.example.auth_service.service.MyJwtRedisService;
import com.example.auth_service.service.UserService;
import com.example.auth_service.utils.JwtUtils;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MyJwtRedisService myJwtRedisService;

    @PermitAll
    @GetMapping("/auth/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader String Authorization){
        String token = Authorization.substring(7);
        if(jwtUtils.isTokenValid(token)){
            Map<String, String> response = new HashMap<>();

            response.put("user_role", jwtUtils.getRole(token));
            response.put("user_username", jwtUtils.extractUsername(token));

            return ResponseEntity.status(HttpStatus.OK.value()).body(response);
        }
        else{
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid token");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
        }
    };

    @PermitAll
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

            String user_id = userService.getUserIdByUsername(loginForm.username());

            token.put("access_token", accessToken);
            token.put("refresh_token", refreshToken);


            long ttl = jwtUtils.extractExpiration(accessToken).getTime() - System.currentTimeMillis();

            // Convert to milliseconds
            myJwtRedisService.saveJwtToRedis(user_id, accessToken, ttl);


            return ResponseEntity.status(HttpStatus.OK.value()).body(token);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(Map.of("message", "Unauthorized"));
        }
    }

    @PermitAll
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

//    @PermitAll
//    @PostMapping("/auth/logout")
//    public ResponseEntity<?> logout(@RequestHeader String Authorization) {
//        String token = Authorization.substring(7);
//        if (jwtUtils.isTokenValid(token)) {
//            String username = jwtUtils.extractUsername(token);
//            String userId = userService.getUserIdByUsername(username);
//            myJwtRedisService.deleteJwtFromRedis(userId, token);
//            return ResponseEntity.status(HttpStatus.OK.value()).body(Map.of("message", "Logged out successfully"));
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(Map.of("message", "Invalid token"));
//        }
//    }
}
