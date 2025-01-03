package com.feis.eduhub.backend;

import java.util.Arrays;

import com.feis.eduhub.backend.common.config.AppBuilder;
import com.feis.eduhub.backend.features.account.AccountController;
import com.feis.eduhub.backend.features.accountClass.controller.ClassMemberController;
import com.feis.eduhub.backend.features.auth.AuthController;
import com.feis.eduhub.backend.features.lesson.LessonController;
import com.feis.eduhub.backend.features.systemClass.SystemClassController;
import com.feis.eduhub.backend.features.systemNotice.controller.SystemNoticeController;

public class App {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder(
                Arrays.asList(
                        new AuthController(),
                        new AccountController(),
                        new SystemClassController(), new ClassMemberController(),
                        new LessonController(),
                        new SystemNoticeController()));
        appBuilder.configureEndpoints(true);
        appBuilder.start();
    }
}
