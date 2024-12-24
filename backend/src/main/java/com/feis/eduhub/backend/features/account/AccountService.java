package com.feis.eduhub.backend.features.account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;
import com.feis.eduhub.backend.features.credentials.Credentials;
import com.feis.eduhub.backend.features.credentials.CredentialsDao;
import com.feis.eduhub.backend.features.credentials.CredentialsService;

/**
 * Service class responsible for managing account-related operations such as
 * functionalities for creating, reading, updating, and
 * deleting account information
 * through the use of the
 * {@link com.feis.eduhub.backend.features.account.AccountDao AccountDao}
 * layer class.
 * 
 * @see AccountDao
 */
public class AccountService {
    private final AccountDao accountDao = new AccountDao();
    private final CredentialsDao credentialsDao = new CredentialsDao();
    private final CredentialsService credentialsService = new CredentialsService();
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

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

    public Account createAccount(Account account, Credentials credentials) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            try {
                accountDao.create(account, conn);
                credentialsService.updateCredentialsData(credentials, account);
                credentialsDao.create(credentials, conn);
                conn.commit();
                return account;
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
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while updating account", e);
        }
    }

    public void deleteAccount(long accountId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            accountDao.delete(accountId, conn);
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while deleting account", e);
        }
    }
}