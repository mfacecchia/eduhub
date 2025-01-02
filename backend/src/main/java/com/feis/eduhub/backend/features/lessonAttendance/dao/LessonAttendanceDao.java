package com.feis.eduhub.backend.features.lessonAttendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.common.lib.Sql;

public class LessonAttendanceDao {
    private final String TABLE_NAME = "lesson_attendance";

    /**
     * Creates multiple attendance records for accounts attending a given lesson.
     * The default value is used for attendance state ({@code false}).
     * 
     * @param lessonId   The ID of the lesson for which attendance is being recorded
     * @param accountsId List of account IDs to mark in the lesson
     * @param conn       Active database connection to execute the query
     * @throws AppException If there's any error during the database operation
     */
    public void create(long lessonId, List<Long> accountsId, Connection conn) throws AppException {
        String query = String.format("INSERT INTO %s (lesson_id, account_id)", TABLE_NAME);
        query = addQueryInserts(query, accountsId.size());
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            setAttendanceParams(ps, lessonId, accountsId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Could not add attendances", e);
        }
    }

    /**
     * Updates the attendance status for a specific account in a given lesson.
     * 
     * @param lessonId  The ID of the lesson for which attendance is being updated
     * @param accountId The ID of the account whose attendance is being updated
     * @param attended  The new attendance status (true if attended, false if not)
     * @param conn      Active database connection to execute the query
     * @throws AppException If there's any error during the database operation
     */
    public void setAttendance(long lessonId, long accountId, boolean attended, Connection conn)
            throws AppException {
        String query = String.format("UPDATE %s SET attended = ? WHERE lesson_id = ? AND account_id = ?", TABLE_NAME);
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            Sql.setParams(ps, Arrays.asList(attended, lessonId, accountId));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Could not add attendance", e);
        }
    }

    /**
     * Generates SQL INSERT query string with multiple value sets.
     * 
     * @param query         The base SQL query string to append VALUES clause to
     * @param elementsCount The number of additional value sets to add (excluding
     *                      the first one)
     * @return A complete SQL INSERT query string with the specified number of value
     *         sets
     * @throws IllegalArgumentException if elementsCount is less than or equal to 0
     */
    private String addQueryInserts(String query, long elementsCount) {
        StringBuilder finalQuery = new StringBuilder(query);
        if (elementsCount <= 0)
            throw new IllegalArgumentException("Invalid elements count");
        finalQuery.append("VALUES (?, ?)");
        for (int i = 0; i < elementsCount - 1; i++) {
            finalQuery.append(", (?, ?)");
        }
        return finalQuery.toString();
    }

    /**
     * Sets parameters for lesson attendance in a prepared statement. Used to
     * bind all accounts to the same lessonId and correctly compile the
     * PreparedStatement.
     * 
     * Uses the utility method {@code setParams} to set the params in the
     * PreparedStatement.
     *
     * @param ps         The prepared statement to set parameters for
     * @param classId    The ID of the class/lesson
     * @param accountsId List of account IDs to add attendance for
     * @throws SQLException If any error occurs while setting the parameters in the
     *                      prepared statement
     */
    private void setAttendanceParams(PreparedStatement ps, long classId, List<Long> accountsId) throws SQLException {
        List<Long> attendanceParams = new ArrayList<>();
        for (long accountId : accountsId) {
            attendanceParams.add(classId);
            attendanceParams.add(accountId);
        }
        Sql.setParams(ps, attendanceParams);
    }
}
