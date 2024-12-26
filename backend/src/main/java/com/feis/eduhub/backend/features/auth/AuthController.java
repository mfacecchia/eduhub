package com.feis.eduhub.backend.features.auth;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.InvalidCredentialsException;
import com.feis.eduhub.backend.common.exceptions.ValidationException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.Hashing;
import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.account.AccountService;
import com.feis.eduhub.backend.features.auth.jwt.JsonWebToken;
import com.feis.eduhub.backend.features.auth.jwt.JwtData;
import com.feis.eduhub.backend.features.auth.jwt.JwtService;
import com.feis.eduhub.backend.features.auth.jwt.errors.TokenGenerationException;
import com.feis.eduhub.backend.features.credentials.Credentials;
import com.feis.eduhub.backend.features.credentials.CredentialsService;
import com.feis.eduhub.backend.features.user.UserDto;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.http.Handler;
import io.javalin.http.SameSite;

public class AuthController implements EndpointsRegister {
    private final String BASE_URL = "/auth";
    private final AccountService accountService = new AccountService();
    private final CredentialsService credentialsService = new CredentialsService();
    private final JwtService jwtService = new JwtService();

    @Override
    public void registerEndpoints(Javalin app) {
        app.before(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL + "/*",
                AuthMiddleware.isLoggedIn(false, true, false));
        app.post(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL + "/login", loginHandler);
        app.post(EndpointsRegister.BASE_V1_ENDPOINT + BASE_URL + "/signup", signupHandler);
    }

    private final Handler signupHandler = (ctx) -> {
        JSONObject json = new JSONObject(ctx.body());
        if (json.isEmpty()) {
            throw new ValidationException("No values provided.");
        }
        // TODO: Add data validation
        UserDto user = createAccount(json);
        ResponseDto<UserDto> response = new ResponseDto.ResponseBuilder<UserDto>(200)
                .withMessage("User created successfully")
                .withData(user)
                .build();
        ctx.status(200).json(response);
    };

    private final Handler loginHandler = (ctx) -> {
        JSONObject json = new JSONObject(ctx.body());
        if (json.isEmpty()) {
            throw new ValidationException("No values provided.");
        }
        Credentials storedCredentials = checkCredentials(json);
        JwtData jwtData = setJwt(storedCredentials, json.optBoolean("rememberMe"));
        setJwtCookie(ctx, jwtData);
        ctx.status(200).result("Authenticated");
    };

    private UserDto createAccount(JSONObject json) throws AppException {
        Account account = getAccountData(json);
        Credentials credentials = getCredentialsData(json);
        accountService.createAccount(account, credentials);
        return generateUser(account, credentials);
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
