package com.feis.eduhub.backend.features.lesson;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;

public class LessonService {
    private final LessonDao lessonDao = new LessonDao();
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public Lesson getLessonById(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return lessonDao.findById(id, conn).orElseGet(null);
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

    public Lesson createLesson(Lesson lesson) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            lessonDao.create(lesson, conn);
            conn.commit();
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
}
