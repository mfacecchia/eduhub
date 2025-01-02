package com.feis.eduhub.backend.features.account;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.ValidationException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.AppEndpoint;
import com.feis.eduhub.backend.features.account.dto.AccountFullInfoDto;
import com.feis.eduhub.backend.features.account.service.AccountFullInfoService;
import com.feis.eduhub.backend.features.account.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements EndpointsRegister {
    private final String BASE_URL = AppEndpoint.DEFAULT_V1.getBaseUrl() + AppEndpoint.ACCOUNT.getBaseUrl();
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

        app.get(BASE_URL, accountInfoHandler());
        app.put(BASE_URL, accountUpdateHandler());
        app.delete(BASE_URL, accountDeleteHandler());
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

    private Handler accountDeleteHandler() {
        return (ctx) -> {
            JSONObject json = new JSONObject(ctx.body());
            Long accountId = json.optLongObject("accountId");
            if (accountId <= 0) {
                throw new ValidationException("Invalid accountId");
            }
            accountService.deleteAccount(accountId);
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(200)
                    .withMessage("User successfully deleted")
                    .build();
            ctx.status(200).json(response);
        };
    }
}
