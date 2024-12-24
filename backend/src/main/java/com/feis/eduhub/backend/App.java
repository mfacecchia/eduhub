package com.feis.eduhub.backend;

import java.util.Arrays;

import com.feis.eduhub.backend.common.config.AppBuilder;
import com.feis.eduhub.backend.features.auth.AuthController;

public class App {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder(Arrays.asList(new AuthController()));
        appBuilder.configureEndpoints(true);
        appBuilder.start();
    }
}
