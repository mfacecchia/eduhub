package com.feis.eduhub.backend.features.lessonAttendance.dto;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LessonDto {
    private Long lessonId;
    private Date lessonDate;
    private Time startsAt;
    private Time endsAt;
    private Integer roomNo;
    private Boolean attended;
}
