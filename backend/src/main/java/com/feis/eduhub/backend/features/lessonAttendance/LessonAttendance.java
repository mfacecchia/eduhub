package com.feis.eduhub.backend.features.lessonAttendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Many-to-Many relation model between
 * {@link com.feis.eduhub.backend.features.account.Account Account} and
 * {@link com.feis.eduhub.backend.features.lesson.Lesson Lesson}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonAttendance {
    @NonNull
    private Long accountId;
    @NonNull
    private Long lessonId;
    @NonNull
    private Boolean attended = false;
}
