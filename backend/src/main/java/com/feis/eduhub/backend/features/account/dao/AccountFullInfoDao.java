package com.feis.eduhub.backend.features.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.feis.eduhub.backend.common.interfaces.dao.Dao;
import com.feis.eduhub.backend.common.lib.Sql;
import com.feis.eduhub.backend.features.account.dto.AccountFullInfoDto;

/**
 * DAO layer class for account full information retrieval from the database.
 * Implements the Dao interface for basic account information retrieval & also
 * includes methods for filtering based on accountId, Email, and
 * roleId.
 * 
 * This class provides methods to:
 * - Find account information by ID
 * - Find account information by email
 * - Retrieve all accounts
 * - Retrieve all users of a certain role
 * 
 * All the queries listed here use INNER JOIN operations with credentials and
 * account_role tables to fetch complete account information.
 * 
 * @see AccountFullInfoDto
 * @see Dao
 */
public class AccountFullInfoDao implements Dao<AccountFullInfoDto> {
    private final String DATABASE_FIELDS = "account.account_id, first_name, last_name, icon, email, account_role.role_id, role_name";
    private final String JOIN_QUERIES = "INNER JOIN credentials ON credentials.account_id = account.account_id INNER JOIN account_role ON account_role.role_id = account.role_id";

    @Override
    public Optional<AccountFullInfoDto> findById(long id, Connection conn) throws SQLException {
        String query = String.format(
                "SELECT %s FROM account %s WHERE account.account_id = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    public Optional<AccountFullInfoDto> findByEmail(String email, Connection conn) throws SQLException {
        String query = String.format("SELECT %s FROM account %s WHERE email = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(email));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    @Override
    public List<AccountFullInfoDto> getAll(Connection conn) throws SQLException {
        List<AccountFullInfoDto> usersList = new ArrayList<>();
        String query = String.format("SELECT %s FROM account %s", DATABASE_FIELDS, JOIN_QUERIES);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            usersList.add(getTableData(rs));
        }
        return usersList;
    }

    public List<AccountFullInfoDto> getAllByRoleId(long id, Connection conn) throws SQLException {
        List<AccountFullInfoDto> usersList = new ArrayList<>();
        String query = String.format("SELECT %s FROM account %s WHERE account.role_id = ?",
                DATABASE_FIELDS, JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            usersList.add(getTableData(rs));
        }
        return usersList;
    }

    private AccountFullInfoDto getTableData(ResultSet rs) throws SQLException {
        return new AccountFullInfoDto(
                (Long) rs.getObject("account_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("icon"),
                (Long) rs.getLong("role_id"),
                rs.getString("role_name"));
    }
}
