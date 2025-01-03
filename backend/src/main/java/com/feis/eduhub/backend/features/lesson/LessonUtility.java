package com.feis.eduhub.backend.features.lesson;

import java.sql.Date;
import java.sql.Time;

import org.json.JSONObject;

/**
 * Utility class providing helper methods for Lesson operations.
 * 
 * @see Lesson
 */
public class LessonUtility {

    private LessonUtility() {
    }

    public static Lesson getLessonFromBody(JSONObject json, boolean includeId) {
        Lesson lesson = new Lesson(
                new Date(json.optLong("lessonDate")),
                new Time(json.optLong("startsAt")),
                new Time(json.optLong("endsAt")),
                json.optIntegerObject("roomNo"),
                json.optLongObject("classId"));
        if (includeId) {
            lesson.setLessonId(json.optLongObject("lessonId"));
        }
        return lesson;
    }
}
