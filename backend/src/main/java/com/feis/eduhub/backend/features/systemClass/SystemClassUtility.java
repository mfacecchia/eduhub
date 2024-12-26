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

    public static SystemClass getClassFromBody(JSONObject json) {
        return new SystemClass(
                json.getString("courseName"),
                json.getString("classAddress"),
                json.getInt("classYear"),
                json.getLong("teacherId"));
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}