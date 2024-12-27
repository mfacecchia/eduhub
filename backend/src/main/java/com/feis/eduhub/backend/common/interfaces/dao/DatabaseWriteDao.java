package com.feis.eduhub.backend.common.interfaces.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface providing methods for creation, update, and deletion on a
 * relational database through an active {@code Connection}.
 * 
 * @param <T> The type of entity this DAO handles
 * 
 * @see SimpleDatabaseReadDao
 */
public interface DatabaseWriteDao<T> {

    T create(T obj, Connection conn) throws SQLException;

    void update(long id, T obj, Connection conn) throws SQLException;

    void delete(long id, Connection conn) throws SQLException;
}
