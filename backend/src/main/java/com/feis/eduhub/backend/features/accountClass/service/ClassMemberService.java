package com.feis.eduhub.backend.features.accountClass.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;
import com.feis.eduhub.backend.features.accountClass.ClassMemberDao;
import com.feis.eduhub.backend.features.accountClass.dto.ClassMemberDto;

public class ClassMemberService {
    private final ClassMemberDao classMemberDao;
    DatabaseConnection databaseConnection;

    public ClassMemberService() {
        classMemberDao = new ClassMemberDao();
        databaseConnection = DatabaseConnection.getInstance();
    }

    public List<ClassMemberDto> getAllClassMembers(long classId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return classMemberDao.getAll(classId, conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public ClassMemberDto getSingleClassMember(long classId, long accountId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return classMemberDao.findByIds(Arrays.asList(classId, accountId), conn).get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Class not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }
}
