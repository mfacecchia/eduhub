package com.feis.eduhub.backend.features.credentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.feis.eduhub.backend.common.interfaces.dao.ModelDao;
import com.feis.eduhub.backend.common.lib.Sql;

public class CredentialsDao implements ModelDao<Credentials> {
    private final String TABLE_NAME = "credentials";

    @Override
    public Optional<Credentials> findById(long id, Connection conn) throws SQLException {
        String query = String.format("SELECT * FROM \"%s\" WHERE credentials_id = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    public Optional<Credentials> findByEmail(String email, Connection conn) throws SQLException {
        String query = String.format("SELECT * FROM \"%s\" WHERE email = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(email));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    @Override
    public List<Credentials> getAll(Connection conn) throws SQLException {
        List<Credentials> credentialsList = new ArrayList<>();
        String query = String.format("SELECT * FROM \"%s\"", TABLE_NAME);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            credentialsList.add(getTableData(rs));
        }
        return credentialsList;
    }

    @Override
    public Credentials create(Credentials credentials, Connection conn) throws SQLException {
        String query = String.format(
                "INSERT INTO \"%s\" (email, password, updated_at, account_id) VALUES (?, ?, ?, ?)",
                TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        Sql.setParams(ps,
                Arrays.asList(credentials.getEmail(), credentials.getPassword(), credentials.getUpdatedAt(),
                        credentials.getAccountId()));
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("User not created");
        }
        credentials.setCredentialsId(generatedKeys.getLong(1));
        return credentials;
    }

    @Override
    public void update(long id, Credentials credentials, Connection conn) throws SQLException {
        String query = String.format(
                "UPDATE \"%s\" SET email = ?, password = ?, updated_at = ? WHERE credentials_id = ?",
                TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(credentials.getEmail(),
                credentials.getPassword(), credentials.getUpdatedAt(), id));
        ps.executeUpdate();
    }

    @Override
    public void delete(long id, Connection conn) throws SQLException {
        String query = String.format("DELETE FROM \"%s\" WHERE credentials_id = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ps.executeUpdate();
    }

    private Credentials getTableData(ResultSet rs) throws SQLException {
        try {
            return new Credentials(
                    (Long) rs.getObject("credentials_id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    (Long) rs.getObject("updated_at"),
                    (Long) rs.getObject("account_id"));
        } catch (NullPointerException e) {
            throw new IllegalStateException("Illegal values found", e);
        }
    }
}
