package com.feis.eduhub.backend.features.role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;

public class SystemRoleService {
    private final SystemRoleDao roleDao = new SystemRoleDao();
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public SystemRole getRoleById(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return roleDao.findById(id, conn).orElseGet(null);
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Role not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<SystemRole> getAllRoles() throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return roleDao.getAll(conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }
}
