package com.feis.eduhub.backend.features.credentials;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;
import com.feis.eduhub.backend.common.lib.Hashing;
import com.feis.eduhub.backend.features.account.Account;

/**
 * Service class responsible for managing credentials-related operations such as
 * functionalities for creating, reading, updating, and
 * deleting credentials information through the use of the
 * {@link com.feis.eduhub.backend.features.account.CredentialsDao
 * CredentialsDao} layer class.
 * 
 * @see CredentialsDao
 */
public class CredentialsService {
    private final CredentialsDao credentialsDao;
    private final DatabaseConnection databaseConnection;

    public CredentialsService() {
        credentialsDao = new CredentialsDao();
        databaseConnection = DatabaseConnection.getInstance();
    }

    public Credentials getCredentialsById(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return credentialsDao.findById(id, conn).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Credentials not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public Credentials getCredentialsByEmail(String email) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return credentialsDao.findByEmail(email, conn).get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Credentials not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<Credentials> getAllCredentials() throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return credentialsDao.getAll(conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public Credentials createCredentials(Credentials credentials) throws AppException {
        setUpdatedAtTime(credentials);
        try (Connection conn = databaseConnection.getConnection()) {
            credentialsDao.create(credentials, conn);
            conn.commit();
            return credentials;
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while creating credentials", e);
        }
    }

    public void updateCredentials(long credentialsId, Credentials credentials) throws AppException {
        setUpdatedAtTime(credentials);
        try (Connection conn = databaseConnection.getConnection()) {
            credentialsDao.update(credentialsId, credentials, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while updating credentials", e);
        }
    }

    public void deleteCredentials(long credentialsId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            credentialsDao.delete(credentialsId, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while deleting credentials", e);
        }
    }

    /**
     * Executes some operations on the credentials before passing them to the DAO
     * layer.
     * Some of the operations are assigning them to an {@code accountId}, hashing
     * the password by using a {@link com.feis.eduhub.backend.common.lib.Hashing
     * Hashing utility} (Argon2id algo) and setting the last updated time to current
     * time (in seconds unit)
     * 
     * @param credentials the credentials object to update
     * @param account     the account from which retrieve all needed information
     * 
     * @throws IllegalStateException if the passed accountId is invalid (lower than
     *                               1)
     * 
     * @see Account
     * @see Hashing
     */
    public void updateCredentialsData(Credentials credentials, Account account) {
        if (account.getAccountId() <= 0) {
            throw new IllegalStateException("accountId cannot be lower than 1");
        }
        credentials.setAccountId(account.getAccountId());
        hashPassword(credentials);
        setUpdatedAtTime(credentials);
    }

    private void hashPassword(Credentials credentials) {
        String hash = Hashing.hash(credentials.getPassword());
        credentials.setPassword(hash);
    }

    /**
     * Updates credentials {@code updatedAt} attribute with current time in seconds
     * (to match JWT's {@code iat} claim time unit).
     * 
     * @param credentials the credentials object to update
     */
    private void setUpdatedAtTime(Credentials credentials) {
        credentials.setUpdatedAt(Instant.now().getEpochSecond());
    }
}
