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

    @NotEmpty(message = "Vui lòng nhập username")
    @Size(min = 8, message = "Username cần ít nhất 8 kí tự trở lên")
    private String username;

    @NotEmpty(message = "Vui lòng nhập password")
    @Size(min = 8, message = "Password cần ít nhất 8 kí tự trở lên")
    private String password;

    @NotEmpty(message = "Vui lòng nhập email")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotEmpty(message = "Vui lòng nhập first name")
    private String firstName;

    @NotEmpty(message = "Vui lòng nhập last name")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotEmpty(message = "Vui lòng nhập city")
    private String city;

    @NotEmpty(message = "Vui lòng nhập phone")
    @Size(min = 10, message = "Phone cần ít nhất 10 kí tự trở lên")
    private String phone;
}
