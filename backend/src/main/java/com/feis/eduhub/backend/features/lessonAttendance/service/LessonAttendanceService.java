package com.feis.eduhub.backend.features.lessonAttendance.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.features.lessonAttendance.dao.LessonAttendanceDao;

public class LessonAttendanceService {
    private final DatabaseConnection databaseConnection;
    private final LessonAttendanceDao lessonAttendanceDao;

    public LessonAttendanceService() {
        databaseConnection = DatabaseConnection.getInstance();
        lessonAttendanceDao = new LessonAttendanceDao();
    }

    public void createAttendance(long lessonId, List<Long> accountsId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            lessonAttendanceDao.create(lessonId, accountsId, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Could not create attendance", e);
        }
    }

    public void setAttendance(long lessonId, long accountId, boolean attended) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            lessonAttendanceDao.setAttendance(lessonId, accountId, attended, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Could not create attendance", e);
        }
    }
}
