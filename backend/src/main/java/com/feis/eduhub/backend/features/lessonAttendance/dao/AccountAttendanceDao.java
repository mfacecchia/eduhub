package com.feis.eduhub.backend.features.lessonAttendance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.feis.eduhub.backend.common.lib.Sql;
import com.feis.eduhub.backend.features.lessonAttendance.dto.AccountAttendanceDto;

public class AccountAttendanceDao {
    private final String DATABASE_FIELDS = "account.account_id, first_name, last_name, icon, attended";
    private final String JOIN_QUERIES = "INNER JOIN account ON account.account_id = lesson_attendance.account_id INNER JOIN lesson ON lesson_attendance.lesson_id = lesson.lesson_id";

    public Optional<AccountAttendanceDto> findById(long lessonId, long accountId, Connection conn) throws SQLException {
        String query = String.format(
                "SELECT %s FROM lesson_attendance %s WHERE lesson_attendance.lesson_id = ? and lesson_attendance.account_id = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(lessonId, accountId));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    public List<AccountAttendanceDto> getAllByLessonId(long lessonId, Connection conn) throws SQLException {
        List<AccountAttendanceDto> attendancesList = new ArrayList<>();
        String query = String.format("SELECT %s FROM lesson_attendance %s WHERE lesson_attendance.lesson_id = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(lessonId));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            attendancesList.add(getTableData(rs));
        }
        return attendancesList;
    }

    private AccountAttendanceDto getTableData(ResultSet rs) throws SQLException {
        return new AccountAttendanceDto(
                (Long) rs.getObject("account_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("icon"),
                rs.getBoolean("attended"));
    }
}
