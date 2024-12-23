package com.feis.eduhub.backend.common.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Base DAO layer interface providing basic read operations. Most suitable for
 * DTO classes (which, by definition, don't need to implement any data
 * manipulation operation).
 * 
 * @param <T> The type of entity this DAO handles
 */
public interface Dao<T> {
    Optional<T> findById(long id, Connection conn) throws SQLException;

    List<T> getAll(Connection conn) throws SQLException;
}
