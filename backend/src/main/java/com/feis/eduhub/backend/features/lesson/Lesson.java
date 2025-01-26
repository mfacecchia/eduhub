package com.feis.eduhub.backend.features.lesson;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.lessonAttendance.LessonAttendance;
import com.feis.eduhub.backend.features.systemClass.SystemClass;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate lessonDate;
    private LocalDateTime startsAt;
    private Time endsAt;
    private Integer roomNo;

    @OneToOne
    private Account createdBy;
    @ManyToOne
    private SystemClass systemClass;
    @ManyToMany
    private Set<Account> account;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<LessonAttendance> studentsAttendandes;
}
