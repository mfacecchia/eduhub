package com.feis.eduhub.backend.features.auth;

import java.util.EnumSet;

import com.feis.eduhub.backend.common.exceptions.UnauthorizedException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.AppEndpoint;
import com.feis.eduhub.backend.common.lib.MiddlewareExecutor;
import com.feis.eduhub.backend.common.middlewares.IsLoggedInMiddleware;
import com.feis.eduhub.backend.common.rbac.AppAction;
import com.feis.eduhub.backend.common.rbac.Rbac;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;

public class AuthMiddleware implements EndpointsRegister {
    private final String BASE_URL = AppEndpoint.DEFAULT_V1.getBaseUrl() + AppEndpoint.AUTH.getBaseUrl();
    private final Rbac rbac;

    public AuthMiddleware() {
        rbac = new Rbac();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.before(BASE_URL + "/login",
                MiddlewareExecutor.executeOnMethod(
                        EnumSet.of(HandlerType.POST),
                        IsLoggedInMiddleware.isLoggedIn(false, true, false, false)));

        app.before(BASE_URL + "/signup",
                MiddlewareExecutor.executeOnMethod(
                        EnumSet.of(HandlerType.POST),
                        IsLoggedInMiddleware.isLoggedIn(true, false, true, false)));

        app.before(BASE_URL + "/logout",
                MiddlewareExecutor.executeOnMethod(
                        EnumSet.of(HandlerType.GET),
                        IsLoggedInMiddleware.isLoggedIn(true, false, true, true)));

        app.before(BASE_URL + "/signup",
                MiddlewareExecutor.executeOnMethod(
                        EnumSet.of(HandlerType.POST),
                        isAllowed()));
    }

    private Handler isAllowed() {
        return (ctx) -> {
            boolean isAllowed = rbac.checkPermissionFromPersistence(ctx.attribute("accountId"),
                    AppAction.MANAGE_ACCOUNTS);
            if (!isAllowed) {
                throw new UnauthorizedException("Not authorized to complete this operation.");
            }
        };
    }
}
