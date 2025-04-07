package com.example.coach_service.component;


import com.example.coach_service.custom.RequireRole;
import com.example.coach_service.handle.CustomResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RoleCheckAspect {
    @Around("@annotation(requireRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequireRole requireRole) throws  Throwable {
        // Lấy request từ RequestContextHolder
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        String rolesHeader = request.getHeader("X-Auth-Roles");

        if (rolesHeader == null || rolesHeader.isEmpty()) {
            throw new CustomResponseStatusException(HttpStatus.UNAUTHORIZED, "Header X-Auth-Roles is missing");
        }

        if(!rolesHeader.contains(requireRole.value())) {
            throw new CustomResponseStatusException(HttpStatus.FORBIDDEN, "You do not have the required role: " + requireRole.value());
        }

        return joinPoint.proceed();
    }
}
