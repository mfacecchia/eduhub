package com.feis.eduhub.backend.common.interfaces.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface representing the more generic
 * {@link com.feis.eduhub.backend.common.interfaces.dao.Dao Dao} interface as a more
 * suitable alternative for app models (which are used to represent SQL Database
 * entities) providing methods for creation, update, and deletion.
 * 
 * @param <T> The type of entity this DAO handles
 * 
 * @see Dao
 */
public interface ModelDao<T> extends Dao<T> {
    T create(T obj, Connection conn) throws SQLException;

    void update(long id, T obj, Connection conn) throws SQLException;

    void delete(long id, Connection conn) throws SQLException;
}
