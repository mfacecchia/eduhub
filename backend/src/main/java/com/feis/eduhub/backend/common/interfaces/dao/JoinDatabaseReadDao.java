package com.feis.eduhub.backend.common.interfaces.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interface for read operations on a relational database tables with joined
 * relationships (e.g. {@code N:N} relations).
 * Alternative to the simpler {@link SimpleDatabaseReadDao} interface, which
 * provides the same methods but for {@code 1:N} tables relations.
 *
 * @param <T> the type of entity this DAO handles
 */
public interface JoinDatabaseReadDao<T> {
    /**
     * Finds a signle entry from the database based on the passed {@code ids}.
     * 
     * @param ids  all the id fields to filter the query for
     * @param conn the connection Object that will be used to execute the query
     * @return a single {@code Optional} entry obtained from the executed database
     *         query.
     * @throws SQLException in case of any database query error (such as abrupt
     *                      disconnection or invalid values passed in the query)
     */
    Optional<T> findByIds(List<Long> ids, Connection conn) throws SQLException;

    /**
     * Takes a single ID to obtain and return all query results in form of a
     * {@code List} Object.
     * 
     * @param id   the ID to filter the query for
     * @param conn the connection Object that will be used to execute the query
     * @return a {@code List} instanced Object with all the returned results from
     *         the executed query
     * @throws SQLException
     */
    List<T> getAll(long id, Connection conn) throws SQLException;
}
