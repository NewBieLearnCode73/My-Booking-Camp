package com.example.profile_service.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // can be used on methods and classes
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME) // annotation is available at runtime
public @interface RequireRole {
    String value(); // ADMIN, CUSTOMER, etc.
}
