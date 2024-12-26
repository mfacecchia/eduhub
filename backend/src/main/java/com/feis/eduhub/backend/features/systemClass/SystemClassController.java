package com.feis.eduhub.backend.features.systemClass;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.ValidationException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class SystemClassController implements EndpointsRegister {
    private final String BASE_URL = SystemClassUtility.getBaseUrl();
    private final SystemClassService systemClassService;

    public SystemClassController() {
        systemClassService = new SystemClassService();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.post(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL, createClass());
        app.put(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL, updateClass());
    }

    private Handler createClass() {
        return (ctx) -> {
            JSONObject json = new JSONObject(ctx.body());
            SystemClass systemClass = SystemClassUtility.getClassFromBody(json, false);
            systemClassService.createClass(systemClass);
            ResponseDto<SystemClass> response = new ResponseDto.ResponseBuilder<SystemClass>(201)
                    .withMessage("Successfully created class")
                    .withData(systemClass)
                    .build();
            ctx.status(201).json(response);
        };
    };

    private Handler updateClass() {
        return (ctx) -> {
            JSONObject json = new JSONObject(ctx.body());
            SystemClass systemClass = SystemClassUtility.getClassFromBody(json, true);
            Long classId = systemClass.getClassId();
            if (classId == null) {
                throw new ValidationException("Invalid Class ID");
            }
            systemClassService.updateClass(classId, systemClass);
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(200)
                    .withMessage("Class successfully updated")
                    .build();
            ctx.status(200).json(response);
        };
    }
}