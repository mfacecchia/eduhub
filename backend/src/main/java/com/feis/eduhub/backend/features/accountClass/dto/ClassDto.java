package com.feis.eduhub.backend.features.accountClass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassDto {
    private Long classId;
    private String courseName;
    private String classAddress;
    private Integer classYear;
    private Long teacherId;
}
