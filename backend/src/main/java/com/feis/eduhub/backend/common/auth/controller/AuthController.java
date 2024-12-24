package com.feis.eduhub.backend.common.auth.controller;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.account.AccountService;
import com.feis.eduhub.backend.features.credentials.Credentials;
import com.feis.eduhub.backend.features.user.UserDto;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthController implements EndpointsRegister {
    private final String BASE_URL = "/auth";
    private final AccountService accountService = new AccountService();

    @Override
    public void registerEndpoints(Javalin app) {
        app.post(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL + "/signup", signupHandler);
    }

    private final Handler signupHandler = (ctx) -> {
        JSONObject json = new JSONObject(ctx.body());
        if (json.isEmpty()) {
            throw new RuntimeException("No values provided.");
        }
        // TODO: Add data validation
        UserDto user = createAccount(json);
        ResponseDto<UserDto> response = new ResponseDto.ResponseBuilder<UserDto>(200)
                .withMessage("User created successfully")
                .withData(user)
                .build();
        ctx.status(200).json(response);
    };

    private UserDto createAccount(JSONObject json) {
        Account account = getAccountData(json);
        Credentials credentials = getCredentialsData(json);
        accountService.createAccount(account, credentials);
        return generateUser(account, credentials);
    }

    private Account getAccountData(JSONObject json) {
        return new Account(json.getString("firstName"), json.getString("lastName"),
                json.getLong("roleId"));
    }

    private Credentials getCredentialsData(JSONObject json) {
        return new Credentials(json.getString("email"), json.getString("password"));
    }

    /**
     * Generates a User object to be embed in HTTP response
     * 
     * @param account     the full account information object
     * @param credentials the full credentials information object
     * @return the generated User DTO
     */
    private UserDto generateUser(Account account, Credentials credentials) {
        return new UserDto(account.getAccountId(), account.getFirstName(), account.getLastName(),
                credentials.getEmail(), account.getIcon(), account.getRoleId());
    }
}
