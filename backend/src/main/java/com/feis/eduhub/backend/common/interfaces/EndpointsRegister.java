package com.feis.eduhub.backend.common.interfaces;

import io.javalin.Javalin;

public interface EndpointsRegister {
    void registerEndpoints(Javalin app);
}
