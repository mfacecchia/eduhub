package com.feis.eduhub.backend.features.lesson;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lesson {
    @NonNull
    private Long lessonId;
    @NonNull
    private Date lessonDate;
    @NonNull
    private Time startsAt;
    @NonNull
    private Time endsAt;
    @NonNull
    private Integer roomNo;
    private Long createdById;
    @NonNull
    private Long classId;

    public Lesson(@NonNull Date lessonDate, @NonNull Time startsAt, @NonNull Time endsAt, @NonNull Integer roomNo,
            @NonNull Long classId) {
        this.lessonDate = lessonDate;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.roomNo = roomNo;
        this.classId = classId;
    }

    public Lesson(@NonNull Date lessonDate, @NonNull Time startsAt, @NonNull Time endsAt, @NonNull Integer roomNo,
            Long createdById, @NonNull Long classId) {
        this.lessonDate = lessonDate;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.roomNo = roomNo;
        this.createdById = createdById;
        this.classId = classId;
    }
}
