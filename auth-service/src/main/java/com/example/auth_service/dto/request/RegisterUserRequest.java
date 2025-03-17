package com.example.auth_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {

    @NotEmpty(message = "Please provide a username")
    @Size(min = 8, message = "Username must be at least 8 characters long")
    private String username;

    @NotEmpty(message = "Please provide a password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotEmpty(message = "Please provide an email")
    @Email(message = "Email not valid")
    private String email;

    @NotEmpty(message = "Please provide a first name")
    private String firstName;

    @NotEmpty(message = "Please provide a last name")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotEmpty(message = "Please provide a city")
    private String city;

    @NotEmpty(message = "Please provide a phone")
    @Size(min = 10, message = "Phone number must be at least 10 characters long")
    private String phone;
}
