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

    // TODO: Add option to not throw if field not found (useful for PUT requests)
    public static SystemClass getClassFromBody(JSONObject json, boolean includeId) {
        SystemClass systemClass = new SystemClass(
                json.getString("courseName"),
                json.getString("classAddress"),
                json.getInt("classYear"),
                json.getLong("teacherId"));
        if (includeId) {
            systemClass.setClassId(json.getLong("classId"));
        }
        return systemClass;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}