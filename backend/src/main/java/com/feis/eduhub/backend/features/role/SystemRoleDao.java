package com.feis.eduhub.backend.features.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.feis.eduhub.backend.common.interfaces.dao.SimpleDatabaseReadDao;
import com.feis.eduhub.backend.common.lib.Sql;

public class SystemRoleDao implements SimpleDatabaseReadDao<SystemRole> {
    private final String TABLE_NAME = "account_role";

    @Override
    public Optional<SystemRole> findById(long id, Connection conn) throws SQLException {
        String query = String.format("SELECT * FROM \"%s\" WHERE role_id = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    @Override
    public List<SystemRole> getAll(Connection conn) throws SQLException {
        List<SystemRole> rolesList = new ArrayList<>();
        String query = String.format("SELECT * FROM \"%s\"", TABLE_NAME);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            rolesList.add(getTableData(rs));
        }
        return rolesList;
    }

    private SystemRole getTableData(ResultSet rs) throws SQLException {
        try {
            return new SystemRole(
                    (Long) rs.getObject("role_id"),
                    rs.getString("role_name"));
        } catch (NullPointerException e) {
            throw new IllegalStateException("Illegal values found", e);
        }
    }
}
