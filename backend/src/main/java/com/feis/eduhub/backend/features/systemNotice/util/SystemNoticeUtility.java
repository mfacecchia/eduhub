package com.feis.eduhub.backend.features.systemNotice.util;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.lib.AppEndpoint;
import com.feis.eduhub.backend.features.systemNotice.SystemNotice;

public class SystemNoticeUtility {
    private static final String BASE_URL = AppEndpoint.DEFAULT_V1.getBaseUrl() + AppEndpoint.NOTICE.getBaseUrl();

    private SystemNoticeUtility() {
    }

    public static SystemNotice getClassFromBody(JSONObject json, boolean includeId) {
        SystemNotice systemNotice = new SystemNotice(
                json.optString("noticeMessage"),
                json.optLongObject("recipientId"));
        if (includeId) {
            systemNotice.setNoticeId(json.optLongObject("noticeId"));
        }
        return systemNotice;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
