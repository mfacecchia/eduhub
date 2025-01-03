package com.feis.eduhub.backend.features.systemNotice.middleware;

import java.util.EnumSet;

import com.feis.eduhub.backend.common.exceptions.UnauthorizedException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.MiddlewareExecutor;
import com.feis.eduhub.backend.common.middlewares.IsLoggedInMiddleware;
import com.feis.eduhub.backend.common.rbac.AppAction;
import com.feis.eduhub.backend.common.rbac.Rbac;
import com.feis.eduhub.backend.features.systemNotice.util.SystemNoticeUtility;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;

public class SystemNoticeMiddleware implements EndpointsRegister {
    private final String BASE_V1_URL = SystemNoticeUtility.getBaseUrl();
    private final Rbac rbac;

    public SystemNoticeMiddleware() {
        rbac = new Rbac();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.before(
                BASE_V1_URL + "/*",
                MiddlewareExecutor.executeOnMethod(
                        EnumSet.allOf(HandlerType.class),
                        IsLoggedInMiddleware.isLoggedIn(true, false, true, false)));

        app.before(
                BASE_V1_URL + "/*",
                MiddlewareExecutor.executeOnMethod(
                        EnumSet.of(HandlerType.POST, HandlerType.PUT, HandlerType.DELETE),
                        isAllowed()));
    }

    private Handler isAllowed() {
        return (ctx) -> {
            boolean isAllowed = rbac.checkPermissionFromPersistence(ctx.attribute("accountId"),
                    AppAction.MANAGE_NOTICES);
            if (!isAllowed) {
                throw new UnauthorizedException("Not authorized to complete this operation.");
            }
        };
    }
}
