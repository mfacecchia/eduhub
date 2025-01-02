package com.feis.eduhub.backend.features.lesson;

import com.feis.eduhub.backend.common.dto.ResponseDto;
import com.feis.eduhub.backend.common.exceptions.ValidationException;
import com.feis.eduhub.backend.common.interfaces.EndpointsRegister;
import com.feis.eduhub.backend.common.lib.AppEndpoint;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LessonController implements EndpointsRegister {
    private final String BASE_V1_URL = AppEndpoint.DEFAULT_V1.getBaseUrl() + AppEndpoint.LESSON.getBaseUrl();
    private final LessonService lessonService;
    private final LessonMiddleware lessonMiddleware;

    public LessonController() {
        lessonService = new LessonService();
        lessonMiddleware = new LessonMiddleware();
    }

    @Override
    public void registerEndpoints(Javalin app) {
        lessonMiddleware.registerEndpoints(app);

        app.get(BASE_V1_URL + "/{lessonId}", lessonInfoHandler());
    }

    private Handler lessonInfoHandler() {
        return (ctx) -> {
            Long lessonId;
            try {
                lessonId = Long.valueOf(ctx.pathParam("lessonId"));
            } catch (NumberFormatException e) {
                throw new ValidationException("Invalid lessonId", e);
            }
            if (lessonId <= 0) {
                throw new ValidationException("Invalid lessonId");
            }
            Lesson lesson = lessonService.getLessonById(lessonId);
            ResponseDto<Lesson> response = new ResponseDto.ResponseBuilder<Lesson>(200)
                    .withMessage("Success")
                    .withData(lesson)
                    .build();
            ctx.status(200).json(response);
        };
    }
}
