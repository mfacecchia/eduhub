package com.feis.eduhub.backend.features.accountClass.controller;

import java.util.List;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.ValidationException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.AppEndpoint;
import com.feis.eduhub.backend.features.accountClass.dto.ClassMemberDto;
import com.feis.eduhub.backend.features.accountClass.service.ClassMemberService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClassMemberController implements EndpointsRegister {
    private final String BASE_V1_URL = AppEndpoint.DEFAULT_V1.getBaseUrl() + AppEndpoint.CLASS.getBaseUrl();
    private final ClassMemberService classMemberService;

    public ClassMemberController() {
        classMemberService = new ClassMemberService();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        app.get(BASE_V1_URL + "/{classId}/members", classMembersHandler());
    }

    private Handler classMembersHandler() {
        return (ctx) -> {
            Long classId;
            try {
                classId = Long.valueOf(ctx.pathParam("classId"));
            } catch (NumberFormatException e) {
                throw new ValidationException("Invalid classId", e);
            }
            if (classId <= 0) {
                throw new ValidationException("Invalid classId");
            }
            List<ClassMemberDto> classMembersList = classMemberService.getAllClassMembers(classId);
            ResponseDto<List<ClassMemberDto>> response = new ResponseDto.ResponseBuilder<List<ClassMemberDto>>(200)
                    .withMessage("Success")
                    .withData(classMembersList)
                    .build();
            ctx.status(200).json(response);
        };
    }
}
