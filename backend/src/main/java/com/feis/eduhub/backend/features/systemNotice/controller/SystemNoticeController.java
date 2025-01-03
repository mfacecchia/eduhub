package com.feis.eduhub.backend.features.systemNotice.controller;

import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.features.systemNotice.middleware.SystemNoticeMiddleware;
import com.feis.eduhub.backend.features.systemNotice.service.SystemNoticeService;
import com.feis.eduhub.backend.features.systemNotice.util.SystemNoticeUtility;

import io.javalin.Javalin;

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
    }
}
