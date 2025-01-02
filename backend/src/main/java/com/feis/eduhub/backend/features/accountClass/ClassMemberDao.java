package com.feis.eduhub.backend.features.accountClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.feis.eduhub.backend.common.interfaces.dao.JoinDatabaseReadDao;
import com.feis.eduhub.backend.common.lib.Sql;
import com.feis.eduhub.backend.features.accountClass.dto.ClassMemberDto;

public class ClassMemberDao implements JoinDatabaseReadDao<ClassMemberDto> {
    private final String DATABASE_FIELDS = "account_class.account_id, first_name, last_name, role_id, icon";
    private final String JOIN_QUERIES = "INNER JOIN account_class ON account_class.class_id = system_class.class_id INNER JOIN account ON account.account_id = account_class.account_id";

    @Override
    public Optional<ClassMemberDto> findByIds(List<Long> ids, Connection conn) throws SQLException {
        String query = String.format(
                "SELECT %s FROM system_class %s WHERE class_id = ? and account_class.account_id = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, ids);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    @Override
    public List<ClassMemberDto> getAll(long id, Connection conn) throws SQLException {
        List<ClassMemberDto> membersList = new ArrayList<>();
        String query = String.format("SELECT %s FROM system_class %s WHERE system_class.class_id = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            membersList.add(getTableData(rs));
        }
        return membersList;
    }

    public List<ClassMemberDto> getAllByLessonId(long id, Connection conn) throws SQLException {
        List<ClassMemberDto> membersList = new ArrayList<>();
        String query = String.format(
                "SELECT %s FROM system_class %s INNER JOIN lesson ON lesson.class_id = system_class.class_id WHERE lesson.lesson_id = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            membersList.add(getTableData(rs));
        }
        return membersList;
    }

    public List<ClassMemberDto> getAllByClassIdAndRoleId(long classId, long roleId, Connection conn)
            throws SQLException {
        List<ClassMemberDto> membersList = new ArrayList<>();
        String query = String.format(
                "SELECT %s FROM system_class %s WHERE system_class.class_id = ? AND account.role_id = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(classId, roleId));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            membersList.add(getTableData(rs));
        }
        return membersList;
    }

    private ClassMemberDto getTableData(ResultSet rs) throws SQLException {
        try {
            return new ClassMemberDto(
                    (Long) rs.getObject("account_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("icon"),
                    (Long) rs.getObject("role_id"));
        } catch (NullPointerException e) {
            throw new IllegalStateException("Illegal values found", e);
        }
    }
}
