package com.feis.eduhub.backend.features.account.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;
import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.account.dao.AccountDao;
import com.feis.eduhub.backend.features.account.dto.AccountFullInfoDto;
import com.feis.eduhub.backend.features.credentials.Credentials;
import com.feis.eduhub.backend.features.credentials.CredentialsDao;
import com.feis.eduhub.backend.features.credentials.CredentialsService;
import com.feis.eduhub.backend.features.role.SystemRole;
import com.feis.eduhub.backend.features.role.SystemRoleService;

/**
 * Service class responsible for managing account-related operations such as
 * functionalities for creating, reading, updating, and deleting account
 * information through the use of the
 * {@link AccountDao} layer class.
 * 
 * @see AccountDao
 */
public class AccountService {
    private final AccountDao accountDao;
    private final CredentialsDao credentialsDao;
    private final CredentialsService credentialsService;
    private final SystemRoleService systemRoleService;
    private final DatabaseConnection databaseConnection;

    public AccountService() {
        accountDao = new AccountDao();
        credentialsDao = new CredentialsDao();
        credentialsService = new CredentialsService();
        systemRoleService = new SystemRoleService();
        databaseConnection = DatabaseConnection.getInstance();
    }

    public Account getAccountById(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return accountDao.findById(id, conn).get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Account not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<Account> getAllAccounts() throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return accountDao.getAll(conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public AccountFullInfoDto createAccount(Account account, Credentials credentials) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            try {
                accountDao.create(account, conn);
                credentialsService.updateCredentialsData(credentials, account);
                credentialsDao.create(credentials, conn);
                conn.commit();
                SystemRole systemRole = systemRoleService.getRoleById(account.getRoleId());
                return AccountFullInfoService.getDto(account, credentials, systemRole);
            } catch (SQLException e) {
                conn.rollback();
                throw new DatabaseQueryException("Could not create account", e);
            }
        } catch (SQLException e) {
            throw new DatabaseQueryException("Could not create account", e);
        }
    }

    public void updateAccount(long accountId, Account account) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            accountDao.update(accountId, account, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while updating account", e);
        }
    }

    public void deleteAccount(long accountId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            accountDao.delete(accountId, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while deleting account", e);
        }
    }
}