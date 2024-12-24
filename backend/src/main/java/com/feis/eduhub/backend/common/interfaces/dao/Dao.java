package com.feis.eduhub.backend.common.interfaces.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Base DAO layer interface providing basic read operations. Most suitable for
 * DTO classes (which, by definition, don't need to implement any data
 * manipulation operation).
 * 
 * @param <T1> The type of entity this DAO handles
 */
public interface Dao<T1> {
    Optional<T1> findById(long id, Connection conn) throws SQLException;

    List<T1> getAll(Connection conn) throws SQLException;
}
