package com.feis.eduhub.backend.features.role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;

public class RoleService {
    private final RoleDao roleDao = new RoleDao();
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public Role getRoleById(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return roleDao.findById(id, conn).orElseGet(null);
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Role not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<Role> getAllRoles() throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return roleDao.getAll(conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }
}
