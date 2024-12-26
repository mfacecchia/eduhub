package com.feis.eduhub.backend.features.systemClass;

import java.util.EnumSet;

import com.feis.eduhub.backend.common.exceptions.UnauthorizedException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.MiddlewareExecutor;
import com.feis.eduhub.backend.common.rbac.AppAction;
import com.feis.eduhub.backend.common.rbac.Rbac;
import com.feis.eduhub.backend.features.auth.AuthMiddleware;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;

/**
 * Middleware handler for System Class related endpoints.
 * This class manages authentication and Role-based authorization for the most
 * "critical" operations (POST, PUT, DELETE request methods).
 * 
 * Implements {@link EndpointsRegister} to register middleware endpoints for
 * the system class feature.
 *
 * @see EndpointsRegister
 * @see Rbac
 * @see AuthMiddleware
 */
public class SystemClassMiddlewares implements EndpointsRegister {
    private final String BASE_URL = SystemClassUtility.getBaseUrl();
    private final Rbac rbac;

    public SystemClassMiddlewares() {
        rbac = new Rbac();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.before(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL,
                MiddlewareExecutor.executeOnMethod(
                        EnumSet.allOf(HandlerType.class),
                        AuthMiddleware.isLoggedIn(true, false, true)));

        app.before(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL,
                MiddlewareExecutor.executeOnMethod(
                        EnumSet.of(HandlerType.POST, HandlerType.PUT, HandlerType.DELETE),
                        isAllowed()));
    }

    private Handler isAllowed() {
        return (ctx) -> {
            boolean isAllowed = rbac.checkPermissionFromPersistence(ctx.attribute("accountId"),
                    AppAction.MANAGE_CLASSES);
            if (!isAllowed) {
                throw new UnauthorizedException("Not authorized to complete this operation.");
            }
        };
    }
}
