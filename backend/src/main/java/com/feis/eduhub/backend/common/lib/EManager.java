package com.feis.eduhub.backend.common.lib;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class for managing JPA EntityManager instances (based on
 * {@code eduhub_punit} persistence unit).
 */
public class EManager {
    private final static EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("eduhub_punit");

    private EManager() {
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
