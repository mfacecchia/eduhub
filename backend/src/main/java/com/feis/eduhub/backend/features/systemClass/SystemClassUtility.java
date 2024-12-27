package com.feis.eduhub.backend.features.systemClass;

import org.json.JSONObject;

/**
 * Utility class providing helper methods for SystemClass operations.
 * 
 * @see SystemClass
 */
public final class SystemClassUtility {
    private static final String BASE_URL = "/class";

    private SystemClassUtility() {
    }

    public static SystemClass getClassFromBody(JSONObject json, boolean includeId) {
        SystemClass systemClass = new SystemClass(
                json.optString("courseName"),
                json.optString("classAddress"),
                json.optIntegerObject("classYear"),
                json.optLongObject("teacherId"));
        if (includeId) {
            systemClass.setClassId(json.optLongObject("classId"));
        }
        return systemClass;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}