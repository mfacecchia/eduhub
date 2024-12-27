package com.feis.eduhub.backend.features.account;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.features.account.dto.AccountFullInfoDto;
import com.feis.eduhub.backend.features.account.service.AccountFullInfoService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements EndpointsRegister {
    private final String BASE_URL = AccountUtility.getBaseUrl();
    private final AccountMiddleware accountMiddleware;
    private final AccountFullInfoService accountFullInfoService;

    public AccountController() {
        accountMiddleware = new AccountMiddleware();
        accountFullInfoService = new AccountFullInfoService();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        accountMiddleware.registerEndpoints(app);
        app.get(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL, accountInfoHandler());
    }

    private Handler accountInfoHandler() {
        return (ctx) -> {
            long accountId = ctx.attribute("accountId");
            AccountFullInfoDto account = accountFullInfoService.getAccountById(accountId);
            ResponseDto<AccountFullInfoDto> response = new ResponseDto.ResponseBuilder<AccountFullInfoDto>(200)
                    .withMessage("Success")
                    .withData(account)
                    .build();
            ctx.status(200).json(response);
        };
    }
}
