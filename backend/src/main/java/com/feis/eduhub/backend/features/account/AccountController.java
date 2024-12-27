package com.feis.eduhub.backend.features.account;

import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;

import io.javalin.Javalin;

public class AccountController implements EndpointsRegister {
    private final String BASE_URL = AccountUtility.getBaseUrl();
    private final AccountMiddleware accountMiddleware;

    public AccountController() {
        accountMiddleware = new AccountMiddleware();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        accountMiddleware.registerEndpoints(app);
    }
}
