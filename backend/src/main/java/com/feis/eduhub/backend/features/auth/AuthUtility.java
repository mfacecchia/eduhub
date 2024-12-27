package com.feis.eduhub.backend.features.auth;

public class AuthUtility {
    private static final String BASE_URL = "/auth";

    private AuthUtility() {
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
