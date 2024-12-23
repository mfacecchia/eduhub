package com.feis.eduhub.backend.common.config;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import com.feis.eduhub.backend.common.lib.Environment;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private final ComboPooledDataSource pool;
    private static final Map<String, String> configValues = Environment
            .getEnvironmentVariables(Arrays.asList("DB_CONNECTION_STRING", "DB_USERNAME", "DB_PASSWORD", "DB_DRIVER"));

    private DatabaseConnection() {
        pool = new ComboPooledDataSource();
        try {
            // Default config
            pool.setDriverClass(configValues.get("DB_DRIVER"));
            pool.setJdbcUrl(configValues.get("DB_CONNECTION_STRING"));
            pool.setUser(configValues.get("DB_USER"));
            pool.setPassword(configValues.get("DB_PASSWORD"));

            // Additional pool config
            pool.setInitialPoolSize(5);
            pool.setMinPoolSize(1);
            pool.setMaxPoolSize(5);
            pool.setAcquireRetryDelay(300);
            pool.setTestConnectionOnCheckin(true);
            pool.setTestConnectionOnCheckout(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Error initializing database connection pool", e);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = pool.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }
}