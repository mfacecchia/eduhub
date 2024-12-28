package com.feis.eduhub.backend.features.auth;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.InvalidCredentialsException;
import com.feis.eduhub.backend.common.exceptions.ValidationException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.Hashing;
import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.account.dto.AccountFullInfoDto;
import com.feis.eduhub.backend.features.account.service.AccountService;
import com.feis.eduhub.backend.features.auth.jwt.JsonWebToken;
import com.feis.eduhub.backend.features.auth.jwt.JwtData;
import com.feis.eduhub.backend.features.auth.jwt.JwtService;
import com.feis.eduhub.backend.features.auth.jwt.errors.TokenGenerationException;
import com.feis.eduhub.backend.features.credentials.Credentials;
import com.feis.eduhub.backend.features.credentials.CredentialsService;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.http.Handler;
import io.javalin.http.SameSite;

public class AuthController implements EndpointsRegister {
    private final String BASE_URL = AuthUtility.getBaseUrl();
    private final AuthMiddleware authMiddleware;
    private final AccountService accountService;
    private final CredentialsService credentialsService;
    private final JwtService jwtService;

    public AuthController() {
        authMiddleware = new AuthMiddleware();
        accountService = new AccountService();
        credentialsService = new CredentialsService();
        jwtService = new JwtService();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        authMiddleware.registerEndpoints(app);

        app.post(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL + "/login", loginHandler());
        app.post(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL + "/signup", signupHandler());
        app.get(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL + "/logout", logoutHandler());
    }

    private Handler signupHandler() {
        return (ctx) -> {
            JSONObject json = new JSONObject(ctx.body());
            if (json.isEmpty()) {
                throw new ValidationException("No values provided.");
            }
            // TODO: Add data validation
            AccountFullInfoDto user = createAccount(json);
            ResponseDto<AccountFullInfoDto> response = new ResponseDto.ResponseBuilder<AccountFullInfoDto>(200)
                    .withMessage("User created successfully")
                    .withData(user)
                    .build();
            ctx.status(200).json(response);
        };
    }

    private Handler loginHandler() {
        return (ctx) -> {
            JSONObject json = new JSONObject(ctx.body());
            if (json.isEmpty()) {
                throw new ValidationException("No values provided.");
            }
            Credentials storedCredentials = checkCredentials(json);
            JwtData jwtData = setJwt(storedCredentials, json.optBoolean("rememberMe"));
            setJwtCookie(ctx, jwtData);
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(200)
                    .withMessage("Successfully authenticated")
                    .build();
            ctx.status(200).json(response);
        };
    }

    private Handler logoutHandler() {
        return (ctx) -> {
            JwtData jwtData = getJwtFromContextObject(ctx);
            jwtService.deleteJwt(jwtData.getJti());
            ctx.removeCookie("sessionId");
            ResponseDto<?> response = new ResponseDto.ResponseBuilder<>(200)
                    .withMessage("Successfully logged out")
                    .build();
            ctx.status(200).json(response);
        };
    }

    private JwtData getJwtFromContextObject(Context ctx) throws AppException {
        Object attribute = ctx.attribute("jwtData");
        if (!(attribute instanceof JwtData)) {
            throw new AppException(400, "Unknown error while logging out",
                    new ClassCastException("Invalid JWT type in context attribute"));
        }
        return (JwtData) attribute;
    }

    private AccountFullInfoDto createAccount(JSONObject json) throws AppException {
        Account account = getAccountData(json);
        Credentials credentials = getCredentialsData(json);
        return accountService.createAccount(account, credentials);
    }

    private Account getAccountData(JSONObject json) throws ValidationException {
        try {
            return new Account(json.optString("firstName", null), json.optString("lastName", null),
                    json.optLongObject("roleId", null));
        } catch (NullPointerException e) {
            throw new ValidationException("Invalid values", e);
        }
    }

    private Credentials getCredentialsData(JSONObject json) {
        return new Credentials(json.getString("email"), json.getString("password"));
    }

    private Credentials checkCredentials(JSONObject json) throws AppException {
        Credentials credentials = getCredentialsData(json);
        Credentials storedCredentials = checkIsPasswordMatching(credentials);
        return storedCredentials;
    }

    private Credentials checkIsPasswordMatching(Credentials credentials) throws AppException {
        Credentials storedCredentials = credentialsService.getCredentialsByEmail(credentials.getEmail());
        if (!Hashing.verify(credentials.getPassword(), storedCredentials.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        return storedCredentials;
    }

    private JwtData setJwt(Credentials credentials, boolean rememberMe) throws TokenGenerationException {
        JwtData jwtData = generateJwt(credentials, rememberMe);
        storeToken(jwtData);
        return jwtData;
    }

    private void storeToken(JwtData jwtData) {
        jwtService.storeJwt(jwtData.getJti(),
                String.valueOf(jwtData.getAccountId()),
                jwtData.getExp());
    }

    private void setJwtCookie(Context ctx, JwtData jwtData) {
        Cookie jwtCookie = new Cookie("sessionId", jwtData.getToken(), "/",
                jwtData.getExpIntDuration(),
                true, 0, true, "", "", SameSite.NONE);
        ctx.cookie(jwtCookie);
    }

    private JwtData generateJwt(Credentials credentials, boolean rememberMe) throws TokenGenerationException {
        if (credentials == null || credentials.getAccountId() <= 0) {
            throw new IllegalStateException("Credentials object is null or account id is lower than 1");
        }
        return JsonWebToken.generateToken(credentials.getAccountId(), rememberMe);
    }
}
