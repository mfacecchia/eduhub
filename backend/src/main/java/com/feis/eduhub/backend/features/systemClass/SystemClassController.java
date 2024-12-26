package com.feis.eduhub.backend.features.systemClass;

import java.util.EnumSet;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.UnauthorizedException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.MiddlewareExecutor;
import com.feis.eduhub.backend.common.rbac.AppAction;
import com.feis.eduhub.backend.common.rbac.Rbac;
import com.feis.eduhub.backend.features.auth.AuthMiddleware;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;

public class SystemClassController implements EndpointsRegister {
    private final String BASE_URL = "/class";
    private final SystemClassService systemClassService;
    private final Rbac rbac;

    public SystemClassController() {
        systemClassService = new SystemClassService();
        rbac = new Rbac();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        // Auth middleware registering (POST method only)
        app.before(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL,
                MiddlewareExecutor.executeOnMethod(EnumSet.of(HandlerType.POST),
                        AuthMiddleware.isLoggedIn(true, false, true)));

        app.post(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL, createClass());
    }

    private Handler createClass() {
        return (ctx) -> {
            JSONObject json = new JSONObject(ctx.body());
            SystemClass systemClass = getClassFromBody(json);
            boolean isAllowed = rbac.checkPermissionFromPersistence(ctx.attribute("accountId"),
                    AppAction.MANAGE_CLASSES);
            if (!isAllowed) {
                throw new UnauthorizedException("Not authorized to complete this operation.");
            }
            systemClassService.createClass(systemClass);
            ResponseDto<SystemClass> response = new ResponseDto.ResponseBuilder<SystemClass>(201)
                    .withMessage("Successfully created class")
                    .withData(systemClass)
                    .build();
            ctx.status(201).json(response);
        };
    };

    private SystemClass getClassFromBody(JSONObject json) {
        return new SystemClass(
                json.getString("courseName"),
                json.getString("classAddress"),
                json.getInt("classYear"),
                json.getLong("teacherId"));
    }
}