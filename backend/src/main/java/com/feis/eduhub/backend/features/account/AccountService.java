package com.feis.eduhub.backend.features.account;

import java.util.List;

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

    public List<Account> getAllAccounts() {
        return accountDao.getAll();
    }

    public Account createAccount(Account account) {
        return accountDao.create(account);
    }

    public void updateAccount(long accountId, Account account) {
        accountDao.update(accountId, account);
    }

    public void deleteAccount(long accountId) {
        accountDao.delete(accountId);
    }
}