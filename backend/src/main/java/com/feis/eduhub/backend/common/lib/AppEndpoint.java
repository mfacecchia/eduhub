package com.feis.eduhub.backend.common.lib;

/**
 * Enumeration representing all available app endpoints which can be used by all
 * controllers as a starting point to build their endpoints.
 * The enum consists in a constant value representing the endpoint name and the
 * actual endpoint (in form of String) as its value.
 */
public enum AppEndpoint {
    DEFAULT_V1("/api/v1"),
    AUTH("/auth"),
    ACCOUNT("/accounts"),
    CLASS("/classes"),
    LESSON("/lessons");

    private final String BASE_URL;

    private AppEndpoint(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }
}
