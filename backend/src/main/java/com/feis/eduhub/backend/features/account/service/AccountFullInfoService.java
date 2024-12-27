package com.feis.eduhub.backend.features.account.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.account.dao.AccountFullInfoDao;
import com.feis.eduhub.backend.features.account.dto.AccountFullInfoDto;
import com.feis.eduhub.backend.features.credentials.Credentials;
import com.feis.eduhub.backend.features.role.SystemRole;

public class AccountFullInfoService {
    private final AccountFullInfoDao accountFullInfoDao;
    private final DatabaseConnection databaseConnection;

    public AccountFullInfoService() {
        accountFullInfoDao = new AccountFullInfoDao();
        databaseConnection = DatabaseConnection.getInstance();
    }

    public AccountFullInfoDto getAccountById(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return accountFullInfoDao.findById(id, conn).get();
        } catch (NoSuchElementException e) {
            throw new DataFetchException("Account not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public AccountFullInfoDto getAccountByEmail(String email) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return accountFullInfoDao.findByEmail(email, conn).get();
        } catch (NoSuchElementException e) {
            throw new DataFetchException("Account not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<AccountFullInfoDto> getAllAccounts() throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return accountFullInfoDao.getAll(conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<AccountFullInfoDto> getAllAccountsByRoleId(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return accountFullInfoDao.getAllByRoleId(id, conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public static AccountFullInfoDto getDto(Account account, Credentials credentials, SystemRole systemRole) {
        return new AccountFullInfoDto(
                account.getAccountId(), account.getFirstName(),
                account.getLastName(), credentials.getEmail(),
                account.getIcon(), systemRole.getRoleId(),
                systemRole.getRoleName());
    }
}
