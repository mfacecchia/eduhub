package com.feis.eduhub.backend.common.lib;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Utility class to simplify and speed up the queries and statements
 * development.
 */
public class Sql {

    /**
     * Sets all the parameters in the passed {@code PreparedStatement}.
     * 
     * @param ps     the prepared statement to set the parameters to
     * @param params all the parameters list
     * @throws SQLException if parameterIndex does not correspond to a parameter
     *                      marker in the SQL statement; if a database access error
     *                      occurs;
     *                      this method is called on a closed
     *                      {@code PreparedStatement}
     *                      or the type of the given object is ambiguous
     * 
     * @see PreparedStatement
     */
    public static void setParams(PreparedStatement ps, List<? extends Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
    }
}
