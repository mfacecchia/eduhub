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

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.interfaces.Dao;
import com.feis.eduhub.backend.common.lib.Sql;

public class CredentialsDao implements Dao<Credentials> {
    private final String TABLE_NAME = "credentials";
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @Override
    public Optional<Credentials> findById(long id) {
        String query = String.format("SELECT * FROM \"%s\" WHERE credentials_id = ?", TABLE_NAME);
        try (Connection conn = databaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            Sql.setParams(ps, Arrays.asList(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(getTableData(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching data", e);
        }
    }

    @Override
    public List<Credentials> getAll() {
        List<Credentials> CredentialssList = new ArrayList<>();
        String query = String.format("SELECT * FROM \"%s\"", TABLE_NAME);
        try (Connection conn = databaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                CredentialssList.add(getTableData(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting all credentials", e);
        }
        return CredentialssList;
    }

    @Override
    public Credentials create(Credentials Credentials) {
        String query = String.format(
                "INSERT INTO \"%s\" (email, password, updated_at, account_id) VALUES (?, ?, ?, ?)",
                TABLE_NAME);
        try (Connection conn = databaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            Sql.setParams(ps,
                    Arrays.asList(Credentials.getEmail(), Credentials.getPassword(), Credentials.getUpdatedAt(),
                            Credentials.getAccountId()));
            ps.executeUpdate();
            conn.commit();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new Exception("User not created");
            }
            Credentials.setCredentialsId(generatedKeys.getLong(1));
            return Credentials;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating Credentials", e);
        }
    }

    @Override
    public void update(long id, Credentials Credentials) {
        String query = String.format(
                "UPDATE \"%s\" SET email = COALESCE(?, email), password = COALESCE(?, password), updated_at = COALESCE(?, updated_at) WHERE credentials_id = ?",
                TABLE_NAME);
        try (Connection conn = databaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            Sql.setParams(ps, Arrays.asList(Credentials.getEmail(),
                    Credentials.getPassword(), Credentials.getUpdatedAt(), id));
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating password", e);
        }
    }

    @Override
    public void delete(long id) {
        String query = String.format("DELETE FROM \"%s\" WHERE credentials_id = ?", TABLE_NAME);
        try (Connection conn = databaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            Sql.setParams(ps, Arrays.asList(id));
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting credentials", e);
        }
    }

    private Credentials getTableData(ResultSet rs) throws SQLException {
        return new Credentials(
                rs.getLong("credentials_id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getInt("updated_at"),
                rs.getLong("account_id"));
    }
}
