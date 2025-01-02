package com.feis.eduhub.backend.features.account;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.lib.AppEndpoint;

public class AccountUtility {
    private static final String BASE_URL = AppEndpoint.DEFAULT_V1.getBaseUrl() + AppEndpoint.ACCOUNT.getBaseUrl();

    private AccountUtility() {
    }

    public static Account getClassFromBody(JSONObject json, boolean includeId) {
        Account account = new Account(
                json.optString("firstName"),
                json.optString("lastName"),
                json.optString("icon"),
                json.optLongObject("roleId"));
        if (includeId) {
            account.setAccountId(json.optLongObject("accountId"));
        }
        return account;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
