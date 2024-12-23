package com.feis.eduhub.backend.features.credentials;

import java.sql.Connection;
import java.time.Instant;
import java.util.List;

import com.feis.eduhub.backend.common.config.DatabaseConnection;

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
    private final CredentialsDao credentialsDao = new CredentialsDao();
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public List<Credentials> getAllCredentials() {
        try (Connection conn = databaseConnection.getConnection()) {
            return credentialsDao.getAll(conn);
        } catch (Exception e) {
            throw new RuntimeException("Could not fetch data", e);
        }
    }

    public Credentials createCredentials(Credentials credentials) {
        setUpdatedAtTime(credentials);
        try (Connection conn = databaseConnection.getConnection()) {
            credentialsDao.create(credentials, conn);
            conn.commit();
            return credentials;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating credentials", e);
        }
    }

    public void updateCredentials(long credentialsId, Credentials credentials) {
        setUpdatedAtTime(credentials);
        try (Connection conn = databaseConnection.getConnection()) {
            credentialsDao.update(credentialsId, credentials, conn);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error while updating credentials", e);
        }
    }

    public void deleteCredentials(long credentialsId) {
        try (Connection conn = databaseConnection.getConnection()) {
            credentialsDao.delete(credentialsId, conn);
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting credentials", e);
        }
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
