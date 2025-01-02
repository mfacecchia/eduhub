package com.feis.eduhub.backend.features.lessonAttendance.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.features.lessonAttendance.dao.AccountAttendanceDao;
import com.feis.eduhub.backend.features.lessonAttendance.dto.AccountAttendanceDto;

public class AccountAttendanceService {
    private final AccountAttendanceDao accountAttendanceDao;
    private final DatabaseConnection databaseConnection;

    public AccountAttendanceService() {
        accountAttendanceDao = new AccountAttendanceDao();
        databaseConnection = DatabaseConnection.getInstance();
    }

    public AccountAttendanceDto getAccountById(long lessonId, long accountId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return accountAttendanceDao.findById(lessonId, accountId, conn).get();
        } catch (NoSuchElementException e) {
            throw new DataFetchException("Account not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<AccountAttendanceDto> getAllAccountsAttendances(long lessonId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return accountAttendanceDao.getAllByLessonId(lessonId, conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }
}
