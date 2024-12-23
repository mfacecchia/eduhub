package com.feis.eduhub.backend.common.interfaces;

import io.javalin.Javalin;

public interface EndpointsRegister {
    static final String BASE_V1_ENDPOINT = "/api/v1";

    void registerEndpoints(Javalin app);
}
