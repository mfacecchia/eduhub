package com.feis.eduhub.backend.features.systemClass;

import java.util.List;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.ValidationException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.features.accountClass.dto.ClassDto;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class SystemClassController implements EndpointsRegister {
    private final String BASE_URL = SystemClassUtility.getBaseUrl();
    private final SystemClassMiddleware systemClassMiddleware;
    private final SystemClassService systemClassService;

    public SystemClassController() {
        systemClassService = new SystemClassService();
        systemClassMiddleware = new SystemClassMiddleware();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        systemClassMiddleware.registerEndpoints(app);

        app.get(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL + "/<classId>", classInfoHandler());
        app.get(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL + "/all", getJoinedClassesHandler());
        app.post(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL, createClass());
        app.put(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL, updateClass());
        app.delete(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL, deleteClass());
    }

    private Handler classInfoHandler() {
        return (ctx) -> {
            Long accountId = ctx.attribute("accountId");
            Long classId;
            try {
                classId = Long.valueOf(ctx.pathParam("classId"));
            } catch (NumberFormatException e) {
                throw new ValidationException("Invalid classId", e);
            }
            if (classId <= 0) {
                throw new ValidationException("Invalid classId");
            }
            ClassDto classDto = systemClassService.getSingleClassByAccountId(accountId, classId);
            ResponseDto<ClassDto> response = new ResponseDto.ResponseBuilder<ClassDto>(200)
                    .withMessage("Success")
                    .withData(classDto)
                    .build();
            ctx.status(200).json(response);
        };
    }

    private Handler getJoinedClassesHandler() {
        return (ctx) -> {
            Long accountId = ctx.attribute("accountId");
            List<ClassDto> classesList = systemClassService.getClassesByAccountId(accountId);
            ResponseDto<List<ClassDto>> response = new ResponseDto.ResponseBuilder<List<ClassDto>>(200)
                    .withMessage("Success")
                    .withData(classesList)
                    .build();
            ctx.status(200).json(response);
        };
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
            if (classId <= 0) {
                throw new ValidationException("Invalid Class ID");
            }
            systemClassService.updateClass(classId, systemClass);
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(200)
                    .withMessage("Class successfully updated")
                    .build();
            ctx.status(200).json(response);
        };
    }

    private Handler deleteClass() {
        return (ctx) -> {
            JSONObject json = new JSONObject(ctx.body());
            Long classId = json.optLongObject("classId");
            if (classId <= 0) {
                throw new ValidationException("Invalid Class ID");
            }
            systemClassService.deleteClass(classId);
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(200)
                    .withMessage("Class successfully deleted")
                    .build();
            ctx.status(200).json(response);
        };
    }
}