package com.feis.eduhub.backend.features.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.feis.eduhub.backend.common.interfaces.Dao;
import com.feis.eduhub.backend.common.lib.Sql;

public class UserDao implements Dao<UserDto> {

    private final String DATABASE_FIELDS = "account.account_id, first_name, last_name, icon, email, role_name";
    private final String JOIN_QUERIES = "INNER JOIN credentials ON credentials.account_id = account.account_id INNER JOIN account_role ON account_role.role_id = account.role_id";

    @Override
    public Optional<UserDto> findById(long id, Connection conn) throws SQLException {
        String query = String.format(
                "SELECT %s FROM account %s WHERE account_id = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    public Optional<UserDto> findByEmail(String email, Connection conn) throws SQLException {
        String query = String.format("SELECT * FROM account %s WHERE email = ?", DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(email));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    @Override
    public List<UserDto> getAll(Connection conn) throws SQLException {
        List<UserDto> usersList = new ArrayList<>();
        String query = String.format("SELECT %s FROM account %s", DATABASE_FIELDS, JOIN_QUERIES);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            usersList.add(getTableData(rs));
        }
        return usersList;
    }

    private UserDto getTableData(ResultSet rs) throws SQLException {
        return new UserDto(rs.getLong("account_id"),
                rs.getString("first_name"), rs.getString("last_name"),
                rs.getString("email"), rs.getString("icon"),
                rs.getString("role_name"));
    }
}
