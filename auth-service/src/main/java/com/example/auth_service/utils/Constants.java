package com.example.auth_service.utils;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final long VALIDITY = TimeUnit.DAYS.toMillis(7);
    public static final long REFRESH = TimeUnit.DAYS.toMillis(30);
}
