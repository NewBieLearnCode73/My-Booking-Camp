package com.example.api_gateway.utils;


import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class EndpointFilter {
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
            Pattern.compile("^/auth/refresh-token$")
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
}
