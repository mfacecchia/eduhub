package com.feis.eduhub.backend.features.account;

import java.sql.Connection;
import java.util.List;

import com.feis.eduhub.backend.common.config.DatabaseConnection;

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
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public List<Account> getAllAccounts() {
        try (Connection conn = databaseConnection.getConnection()) {
            return accountDao.getAll(conn);
        } catch (Exception e) {
            throw new RuntimeException("Could not fetch data", e);
        }
    }

    public Account createAccount(Account account) {
        try (Connection conn = databaseConnection.getConnection()) {
            Account createdAcc = accountDao.create(account, conn);
            // TODO: WIP
            return createdAcc;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating account", e);
        }
    }

    public void updateAccount(long accountId, Account account) {
        try (Connection conn = databaseConnection.getConnection()) {
            accountDao.update(accountId, account, conn);
        } catch (Exception e) {
            throw new RuntimeException("Error while updating account", e);
        }
    }

    public void deleteAccount(long accountId) {
        try (Connection conn = databaseConnection.getConnection()) {
            accountDao.delete(accountId, conn);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting account", e);
        }
    }
}