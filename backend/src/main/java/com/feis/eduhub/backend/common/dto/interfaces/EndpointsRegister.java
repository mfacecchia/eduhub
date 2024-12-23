package com.feis.eduhub.backend.common.dto.interfaces;

import io.javalin.Javalin;

public interface EndpointsRegister {
    void registerEndpoints(Javalin app);
}
