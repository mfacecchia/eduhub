package com.feis.eduhub.backend.features.systemNotice.controller;

import java.util.List;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.ContextUtil;
import com.feis.eduhub.backend.features.systemNotice.SystemNotice;
import com.feis.eduhub.backend.features.systemNotice.dto.SystemNoticeFullInfoDto;
import com.feis.eduhub.backend.features.systemNotice.middleware.SystemNoticeMiddleware;
import com.feis.eduhub.backend.features.systemNotice.service.SystemNoticeFullInfoService;
import com.feis.eduhub.backend.features.systemNotice.service.SystemNoticeService;
import com.feis.eduhub.backend.features.systemNotice.util.SystemNoticeUtility;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class SystemNoticeController implements EndpointsRegister {
    private final String BASE_V1_ENDPOINT = SystemNoticeUtility.getBaseUrl();
    private final SystemNoticeService systemNoticeService;
    private final SystemNoticeMiddleware systemNoticeMiddleware;
    private final SystemNoticeFullInfoService systemNoticeFullInfoService;

    public SystemNoticeController() {
        systemNoticeMiddleware = new SystemNoticeMiddleware();
        systemNoticeService = new SystemNoticeService();
        systemNoticeFullInfoService = new SystemNoticeFullInfoService();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        systemNoticeMiddleware.registerEndpoints(app);

        app.get(BASE_V1_ENDPOINT, noticesListHandler());
        app.get(BASE_V1_ENDPOINT + "/{noticeId}", noticeInfoHandler());
        app.post(BASE_V1_ENDPOINT, createNoticeHandler());
        app.delete(BASE_V1_ENDPOINT + "/{noticeId}", deleteNoticeHandler());
    }

    private Handler noticeInfoHandler() {
        return (ctx) -> {
            long noticeId = ContextUtil.getIdFromPath(ctx, "noticeId");
            SystemNoticeFullInfoDto notice = systemNoticeFullInfoService.getNoticeById(noticeId);
            ResponseDto<SystemNoticeFullInfoDto> response = new ResponseDto.ResponseBuilder<SystemNoticeFullInfoDto>(
                    200)
                    .withMessage("Success")
                    .withData(notice)
                    .build();
            ctx.status(201).json(response);
        };
    }

    private Handler noticesListHandler() {
        return (ctx) -> {
            List<SystemNoticeFullInfoDto> noticesList = systemNoticeFullInfoService.getAllNotices();
            ResponseDto<List<SystemNoticeFullInfoDto>> response = new ResponseDto.ResponseBuilder<List<SystemNoticeFullInfoDto>>(
                    200)
                    .withMessage(String.format("Found %d entries", noticesList.size()))
                    .withData(noticesList)
                    .build();
            ctx.status(201).json(response);
        };
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

    private Handler deleteNoticeHandler() {
        return (ctx) -> {
            long noticeId = ContextUtil.getIdFromPath(ctx, "noticeId");
            systemNoticeService.deleteNotice(noticeId);
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(200)
                    .withMessage("Success")
                    .build();
            ctx.status(200).json(response);
        };
    }
}
