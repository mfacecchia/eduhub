package com.feis.eduhub.backend.features.lessonAttendance.dto;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LessonDto {
    private Long lessonId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date lessonDate;
    private Time startsAt;
    private Time endsAt;
    private Integer roomNo;
    private Boolean attended;
}
