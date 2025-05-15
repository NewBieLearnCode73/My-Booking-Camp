package com.example.auth_service.controller;

import com.example.auth_service.custom.RequireRole;
import com.example.auth_service.dto.request.RegisterUserRequest;
import com.example.auth_service.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/auth/activate")
    public ResponseEntity<?> activateUser(@RequestParam String code){
        userService.activateUser(code);
        return ResponseEntity.status(HttpStatus.OK).body("User activated successfully");
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN"})
    @GetMapping("/auth/is-owner-existed/{ownerId}")
    public ResponseEntity<?> isOwnerExist(@PathVariable String ownerId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.isOwnerExist(ownerId));
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN"})
    @GetMapping("/auth/get-basic-info-by-username/{username}")
    public ResponseEntity<?> getBasicInfoByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getBasicInfoByUsername(username));
    }

    @RequireRole({"ROLE_ADMIN"})
    @GetMapping("/auth/get-username-by-id/{id}")
    public ResponseEntity<?> getUsernameById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsernameById(id));
    }

    // Register
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(registerUserRequest));
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN", "ROLE_OWNER"})
    @PostMapping("/auth/change-customer-to-staff/{id}/{companyId}")
    public ResponseEntity<?> changeCustomerToStaff(@PathVariable String id, @PathVariable String companyId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.changeCustomerToStaff(id, companyId));
    }

    @RequireRole({"ROLE_MASTER","ROLE_ADMIN"})
    @PutMapping("/auth/change-customer-to-owner/{id}")
    public ResponseEntity<?> changeCustomerToOwner(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.changeCustomerToOwner(id));
    }
}