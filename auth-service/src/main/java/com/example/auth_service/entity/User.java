package com.example.auth_service.entity;

import com.example.auth_service.utils.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", nullable = false)
    @NotEmpty(message = "Vui lòng nhập username")
    @Size(min = 8, message = "Username cần ít nhất 8 kí tự trở lên")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Vui lòng nhập password")
    @Size(min = 8, message = "Password cần ít nhất 8 kí tự trở lên")
    private String password;

    @Column(name = "email", nullable = false)
    @NotEmpty(message = "Vui lòng nhập email")
    @Email(message = "Email không hợp lệ")
    private String email;

    @Column(name = "activation_code")
    @UuidGenerator
    private String activationCode;

    @Column(name = "activation", nullable = false)
    private Boolean activation = false;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}

