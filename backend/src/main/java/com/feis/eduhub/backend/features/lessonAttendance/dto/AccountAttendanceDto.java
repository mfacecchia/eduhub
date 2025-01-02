package com.feis.eduhub.backend.features.lessonAttendance.dto;

import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.lesson.Lesson;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object representing attendance information for an account.
 * 
 * This DTO is resulting from the N:N relation between the {@code Account} and
 * {@code Lesson} database entities
 * 
 * @see Account
 * @see Lesson
 */
@AllArgsConstructor
@Data
public class AccountAttendanceDto {
    private Long accountId;
    private String firstName;
    private String lastName;
    private String icon;
    private Boolean attended;
}
