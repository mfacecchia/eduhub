package com.feis.eduhub.backend.features.systemClass;

import org.json.JSONObject;

import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;

import io.javalin.Javalin;

public class SystemClassController implements EndpointsRegister {
    private final String BASE_URL = "/class";

    @Override
    public void registerEndpoints(Javalin app) {
        // TODO: Add implementation
    }

    private SystemClass getClassFromBody(JSONObject json) {
        return new SystemClass(
                json.getString("courseName"),
                json.getString("classAddress"),
                json.getInt("classYear"),
                json.getLong("teacherId"));
    }
}