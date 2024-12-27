package com.feis.eduhub.backend.features.account;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.ValidationException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.features.account.dto.AccountFullInfoDto;
import com.feis.eduhub.backend.features.account.service.AccountFullInfoService;
import com.feis.eduhub.backend.features.account.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements EndpointsRegister {
    private final String BASE_URL = AccountUtility.getBaseUrl();
    private final AccountMiddleware accountMiddleware;
    private final AccountService accountService;
    private final AccountFullInfoService accountFullInfoService;

    public AccountController() {
        accountMiddleware = new AccountMiddleware();
        accountService = new AccountService();
        accountFullInfoService = new AccountFullInfoService();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        accountMiddleware.registerEndpoints(app);

        app.get(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL, accountInfoHandler());
        app.put(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL, accountUpdateHandler());
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

    private Handler accountUpdateHandler() {
        return (ctx) -> {
            JSONObject json = new JSONObject(ctx.body());
            Account account = AccountUtility.getClassFromBody(json, true);
            if (account.getAccountId() <= 0) {
                throw new ValidationException("Invalid accountId");
            }
            accountService.updateAccount(account.getAccountId(), account);
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(200)
                    .withMessage("User info successfully updated")
                    .build();
            ctx.status(200).json(response);
        };
    }
}
