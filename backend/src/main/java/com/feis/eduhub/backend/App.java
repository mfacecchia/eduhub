package com.feis.eduhub.backend;

import com.feis.eduhub.backend.common.config.AppBuilder;

public class App {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder(null);
        appBuilder.configureEndpoints(true);
        appBuilder.start();
    }
}
