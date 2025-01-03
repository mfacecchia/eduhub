package com.feis.eduhub.backend.features.lesson;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;
import com.feis.eduhub.backend.features.accountClass.ClassMemberDao;
import com.feis.eduhub.backend.features.accountClass.dto.ClassMemberDto;
import com.feis.eduhub.backend.features.lessonAttendance.dao.LessonAttendanceDao;
import com.feis.eduhub.backend.features.lessonAttendance.dto.LessonDto;

public class LessonService {
    private final LessonDao lessonDao;
    private final LessonAttendanceDao lessonAttendanceDao;
    private final ClassMemberDao classMemberDao;
    private final DatabaseConnection databaseConnection;

    public LessonService() {
        lessonDao = new LessonDao();
        lessonAttendanceDao = new LessonAttendanceDao();
        classMemberDao = new ClassMemberDao();
        databaseConnection = DatabaseConnection.getInstance();
    }

    public Lesson getLessonById(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return lessonDao.findById(id, conn).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Lesson not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<Lesson> getLessonByClassId(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return lessonDao.findByClassId(id, conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<Lesson> getAllLessons() throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return lessonDao.getAll(conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<LessonDto> getAllUpcomingLessons(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return lessonDao.getAllUpcomingByAccountId(id, conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public Lesson createLesson(Lesson lesson) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            try {
                lessonDao.create(lesson, conn);
                setDefaultAttendances(lesson.getClassId(), lesson.getLessonId(), conn);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new DatabaseQueryException("Cannot create lesson", e);
            }
            return lesson;
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while creating lesson", e);
        }
    }

    public void updateLesson(long lessonId, Lesson lesson) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            lessonDao.update(lessonId, lesson, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while updating lesson", e);
        }
    }

    public void deleteLesson(long lessonId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            lessonDao.delete(lessonId, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while deleting lesson", e);
        }
    }

    public List<LessonDto> getLessonsByAccountId(long accountId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return lessonDao.findAllByAccountId(accountId, conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public LessonDto getSingleLessonByAccountId(long accountId, long lessonId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return lessonDao.findSingleByAccountId(accountId, lessonId, conn).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Class not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    /**
     * Sets default attendance records for all students in a class for a specific
     * lesson.
     * 
     * @param classId  The ID of the class for which to set attendance records
     * @param lessonId The ID of the lesson for which to set attendance records
     * @param conn     The database connection to use for the operation
     * @throws AppException If any custom app error gets thrown by any calling
     *                      method
     * @throws SQLException If there is a database error while setting the
     *                      attendance
     */
    private void setDefaultAttendances(long classId, long lessonId, Connection conn) throws AppException, SQLException {
        List<ClassMemberDto> classMembers = classMemberDao.getAllByClassIdAndRoleId(classId, 3L, conn);
        List<Long> classMembersIds = getMemebrsIds(classMembers);
        lessonAttendanceDao.create(lessonId, classMembersIds, conn);
    }

    /**
     * Extracts account IDs from a list of {@code ClassMemberDto} objects.
     *
     * @param membersList List of ClassMemberDto objects containing member
     *                    information
     * @return List of Long values representing the account IDs of all members
     * 
     * @see ClassMemberDto
     */
    private List<Long> getMemebrsIds(List<ClassMemberDto> membersList) {
        List<Long> membersIds = new ArrayList<>();
        for (ClassMemberDto member : membersList) {
            membersIds.add(member.getAccountId());
        }
        return membersIds;
    }
}
