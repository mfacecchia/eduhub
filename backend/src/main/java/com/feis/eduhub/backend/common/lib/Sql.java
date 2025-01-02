package com.feis.eduhub.backend.common.lib;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
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
     * @param params all the parameters list.
     * @throws SQLException if parameterIndex does not correspond to a parameter
     *                      marker in the SQL statement; if a database access error
     *                      occurs;
     *                      this method is called on a closed
     *                      {@code PreparedStatement}
     *                      or the type of the given object is ambiguous
     * @apiNote if you want to pass a list of params in the {@code params} list, it
     *          is possible to do so by passing another instance of any
     *          {@code Collection}.
     *          Passing arrays as value will possibly result in unintended behavior.
     * 
     * @see PreparedStatement
     */
    public static void setParams(PreparedStatement ps, List<? extends Object> params) throws SQLException {
        int paramPositionCounter = 1;
        for (Object param : params) {
            if (param instanceof Collection paramsList) {
                for (Object listParam : paramsList) {
                    ps.setObject(paramPositionCounter, listParam);
                    paramPositionCounter++;
                }
                continue;
            }
            ps.setObject(paramPositionCounter, param);
            paramPositionCounter++;
        }
    }
}
