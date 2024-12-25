package com.feis.eduhub.backend.features.systemClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;

public class SystemClassService {
    private final SystemClassDao systemClassDao = new SystemClassDao();
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public SystemClass getClassById(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return systemClassDao.findById(id, conn).orElseGet(null);
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Class not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<SystemClass> getClassByTeacherId(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return systemClassDao.findByTeacherId(id, conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<SystemClass> getAllClasses() throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return systemClassDao.getAll(conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public SystemClass createClass(SystemClass systemClass) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            systemClassDao.create(systemClass, conn);
            conn.commit();
            return systemClass;
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while creating class", e);
        }
    }

    public void updateClass(long classId, SystemClass systemClass) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            systemClassDao.update(classId, systemClass, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while updating class", e);
        }
    }

    public void deleteClass(long classId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            systemClassDao.delete(classId, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while deleting class", e);
        }
    }
}
