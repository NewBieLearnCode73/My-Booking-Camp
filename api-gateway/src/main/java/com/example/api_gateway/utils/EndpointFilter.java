package com.example.api_gateway.utils;


import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class EndpointFilter {

    private static final List<Pattern> SWAGGERS_ENDPOINTS = Arrays.asList(
            // Đường dẫn của các service với đuôi index.html
            Pattern.compile("^/auth/api-docs$"),
            Pattern.compile("^/auth/swagger-ui/index.html$"),

            Pattern.compile("^/profile/api-docs$"),
            Pattern.compile("^/profile/swagger-ui/index.html$"),

            Pattern.compile("^/notification/api-docs$"),
            Pattern.compile("^/notification/swagger-ui/index.html$"),

            Pattern.compile("^/booking/api-docs$"),
            Pattern.compile("^/booking/swagger-ui/index.html$"),

            Pattern.compile("^/coach/api-docs$"),
            Pattern.compile("^/coach/swagger-ui/index.html$"),

            Pattern.compile("^/company/api-docs$"),
            Pattern.compile("^/company/swagger-ui/index.html$"),

            Pattern.compile("^/driver/api-docs$"),
            Pattern.compile("^/driver/swagger-ui/index.html$"),

            Pattern.compile("^/payment/api-docs$"),
            Pattern.compile("^/payment/swagger-ui/index.html$"),

            Pattern.compile("^/route/api-docs$"),
            Pattern.compile("^/route/swagger-ui/index.html$"),

            Pattern.compile("^/trip/api-docs$"),
            Pattern.compile("^/trip/swagger-ui/index.html$")
    );


    private static final List<Pattern> GET_PUBLIC_ENDPOINTS = Arrays.asList(
            Pattern.compile("^/auth/validate-token$"),
            Pattern.compile("^/auth/activate\\?.+$"),
            Pattern.compile("^/booking/get-all-seats-was-booked/([\\w-]+)$"),
            Pattern.compile("^/coach/([\\w-]+)$"),
            Pattern.compile("^/company/([\\w-]+)$"),
            Pattern.compile("^/route$"),
            Pattern.compile("^/route\\?.+$"),
            Pattern.compile("^/route/([\\w-]+)$"),
            Pattern.compile("^/trip$"),
            Pattern.compile("^/trip/([\\w-]+)$"),
            Pattern.compile("^/trip\\?.+$"),
            Pattern.compile("^/trip/departureDateTime\\?.+$"),
            Pattern.compile("^/trip/departureDate\\?.+$")
    );


    private static final List<Pattern> POST_PUBLIC_ENDPOINTS = Arrays.asList(
            Pattern.compile("^/auth/login$"),
            Pattern.compile("^/auth/register$"),
            Pattern.compile("^/auth/refresh-token$"),
            Pattern.compile("^/auth/logout$")
    );


    public static boolean isPublicEndpoint(String requestUri) {
        for (Pattern pattern : GET_PUBLIC_ENDPOINTS) {
            if (pattern.matcher(requestUri).matches()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPublicPostEndpoint(String requestUri) {
        for (Pattern pattern : POST_PUBLIC_ENDPOINTS) {
            if (pattern.matcher(requestUri).matches()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSwaggerEndpoint(String requestUri) {
        for (Pattern pattern : SWAGGERS_ENDPOINTS) {
            if (pattern.matcher(requestUri).matches()) {
                return true;
            }
        }
        return false;
    }
}
