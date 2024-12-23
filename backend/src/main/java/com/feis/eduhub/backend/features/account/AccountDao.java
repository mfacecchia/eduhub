package com.feis.eduhub.backend.features.account;

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
import com.feis.eduhub.backend.common.interfaces.ModelDao;
import com.feis.eduhub.backend.common.lib.Sql;

public class AccountDao implements ModelDao<Account> {
    private final String TABLE_NAME = "account";
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @Override
    public Optional<Account> findById(long id) {
        String query = String.format("SELECT * FROM \"%s\" WHERE account_id = ?", TABLE_NAME);
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
    public List<Account> getAll() {
        List<Account> accountsList = new ArrayList<>();
        String query = String.format("SELECT * FROM \"%s\"", TABLE_NAME);
        try (Connection conn = databaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                accountsList.add(getTableData(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting all accounts", e);
        }
        return accountsList;
    }

    @Override
    public Account create(Account account) {
        String query = String.format(
                "INSERT INTO \"%s\" (first_name, last_name, icon, role_id) VALUES (?, ?, ?, ?)",
                TABLE_NAME);
        try (Connection conn = databaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            Sql.setParams(ps, Arrays.asList(account.getFirstName(), account.getLastName(), account.getIcon(),
                    account.getRoleId()));
            ps.executeUpdate();
            conn.commit();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new Exception("User not created");
            }
            account.setAccountId(generatedKeys.getLong(1));
            return account;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating account", e);
        }
    }

    @Override
    public void update(long id, Account account) {
        String query = String.format(
                "UPDATE \"%s\" SET first_name = COALESCE(?, first_name), last_name = COALESCE(?, last_name), icon = COALESCE(?, icon), role_id = COALESCE(?, role_id) WHERE account_id = ?",
                TABLE_NAME);
        try (Connection conn = databaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            Sql.setParams(ps, Arrays.asList(account.getFirstName(),
                    account.getLastName(), account.getIcon(),
                    account.getRoleId(), id));
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating user", e);
        }
    }

    @Override
    public void delete(long id) {
        String query = String.format("DELETE FROM \"%s\" WHERE account_id = ?", TABLE_NAME);
        try (Connection conn = databaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            Sql.setParams(ps, Arrays.asList(id));
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting user", e);
        }
    }

    private Account getTableData(ResultSet rs) throws SQLException {
        return new Account(
                rs.getLong("account_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("icon"),
                rs.getInt("role_id"));
    }
}