package com.feis.eduhub.backend.features.credentials;

import java.time.Instant;
import java.util.List;

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

    public List<Credentials> getAllCredentials() {
        return credentialsDao.getAll();
    }

    public Credentials createCredentials(Credentials credentials) {
        setUpdatedAtTime(credentials);
        return credentialsDao.create(credentials);
    }

    public void updateCredentials(long credentialsId, Credentials credentials) {
        setUpdatedAtTime(credentials);
        credentialsDao.update(credentialsId, credentials);
    }

    public void deleteCredentials(long credentialsId) {
        credentialsDao.delete(credentialsId);
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
