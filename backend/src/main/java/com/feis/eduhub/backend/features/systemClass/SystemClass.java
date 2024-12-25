package com.feis.eduhub.backend.features.systemClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SystemClass {
    @NonNull
    private Long classId;
    @NonNull
    private String courseName;
    @NonNull
    private String classAddress;
    @NonNull
    private Integer classYear;
    private Long teacherId;

    public SystemClass(@NonNull String courseName, @NonNull String classAddress, @NonNull Integer classYear) {
        this.courseName = courseName;
        this.classAddress = classAddress;
        this.classYear = classYear;
    }

    public SystemClass(@NonNull String courseName, @NonNull String classAddress, @NonNull Integer classYear,
            Long teacherId) {
        this.courseName = courseName;
        this.classAddress = classAddress;
        this.classYear = classYear;
        this.teacherId = teacherId;
    }
}
