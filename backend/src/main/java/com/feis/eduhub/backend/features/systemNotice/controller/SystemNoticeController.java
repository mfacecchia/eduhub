package com.feis.eduhub.backend.features.systemNotice.controller;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.features.systemNotice.SystemNotice;
import com.feis.eduhub.backend.features.systemNotice.middleware.SystemNoticeMiddleware;
import com.feis.eduhub.backend.features.systemNotice.service.SystemNoticeService;
import com.feis.eduhub.backend.features.systemNotice.util.SystemNoticeUtility;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class SystemNoticeController implements EndpointsRegister {
    private final String BASE_V1_ENDPOINT = SystemNoticeUtility.getBaseUrl();
    private final SystemNoticeService systemNoticeService;
    private final SystemNoticeMiddleware systemNoticeMiddleware;

    public SystemNoticeController() {
        systemNoticeMiddleware = new SystemNoticeMiddleware();
        systemNoticeService = new SystemNoticeService();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        systemNoticeMiddleware.registerEndpoints(app);

        app.post(BASE_V1_ENDPOINT, createNoticeHandler());
    }

    private Handler createNoticeHandler() {
        return (ctx) -> {
            long accountId = ctx.attribute("accountId");
            JSONObject json = new JSONObject(ctx.body());
            SystemNotice systemNotice = SystemNoticeUtility.getClassFromBody(json, false);
            systemNotice.setSenderId(accountId);
            systemNoticeService.createNotice(systemNotice);
            ResponseDto<SystemNotice> response = new ResponseDto.ResponseBuilder<SystemNotice>(201)
                    .withMessage("Success")
                    .withData(systemNotice)
                    .build();
            ctx.status(201).json(response);
        };
    }
}
