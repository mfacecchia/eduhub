package com.feis.eduhub.backend.features.account;

import java.util.EnumSet;

import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.MiddlewareExecutor;
import com.feis.eduhub.backend.common.middlewares.IsLoggedInMiddleware;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;

public class AccountMiddleware implements EndpointsRegister {
    private final String BASE_URL = AccountUtility.getBaseUrl();

    public AccountMiddleware() {
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.before(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL,
                MiddlewareExecutor.executeOnMethod(
                        EnumSet.allOf(HandlerType.class),
                        IsLoggedInMiddleware.isLoggedIn(true, false, true, false)));
    }
}
