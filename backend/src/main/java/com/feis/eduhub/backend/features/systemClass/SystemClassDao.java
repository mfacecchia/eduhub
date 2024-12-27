package com.feis.eduhub.backend.features.systemClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.feis.eduhub.backend.common.interfaces.dao.DatabaseWriteDao;
import com.feis.eduhub.backend.common.interfaces.dao.SimpleDatabaseReadDao;
import com.feis.eduhub.backend.common.lib.Sql;
import com.feis.eduhub.backend.features.accountClass.dto.ClassDto;

public class SystemClassDao implements SimpleDatabaseReadDao<SystemClass>, DatabaseWriteDao<SystemClass> {
    private final String TABLE_NAME = "system_class";

    @Override
    public Optional<SystemClass> findById(long id, Connection conn) throws SQLException {
        String query = String.format("SELECT * FROM \"%s\" WHERE class_id = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    public List<SystemClass> findByTeacherId(long id, Connection conn) throws SQLException {
        List<SystemClass> classesList = new ArrayList<>();
        String query = String.format("SELECT * FROM \"%s\" WHERE teacher_id = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            classesList.add(getTableData(rs));
        }
        return classesList;
    }

    @Override
    public List<SystemClass> getAll(Connection conn) throws SQLException {
        List<SystemClass> classesList = new ArrayList<>();
        String query = String.format("SELECT * FROM \"%s\"", TABLE_NAME);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            classesList.add(getTableData(rs));
        }
        return classesList;
    }

    @Override
    public SystemClass create(SystemClass systemClass, Connection conn) throws SQLException {
        String query = String.format(
                "INSERT INTO \"%s\" (course_name, class_address, class_year, teacher_id) VALUES (?, ?, ?, ?)",
                TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query, new String[] { "class_id" });
        Sql.setParams(ps,
                Arrays.asList(systemClass.getCourseName(), systemClass.getClassAddress(),
                        systemClass.getClassYear(), systemClass.getTeacherId()));
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Lesson not created");
        }
        systemClass.setClassId(generatedKeys.getLong("class_id"));
        return systemClass;
    }

    // TODO: Throw on element not found
    @Override
    public void update(long id, SystemClass systemClass, Connection conn) throws SQLException {
        String query = String.format(
                "UPDATE \"%s\" SET course_name = ?, class_address = ?, class_year = ?, teacher_id = ? WHERE class_id = ?",
                TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps,
                Arrays.asList(systemClass.getCourseName(), systemClass.getClassAddress(), systemClass.getClassYear(),
                        systemClass.getTeacherId(), id));
        ps.executeUpdate();
    }

    // TODO: Throw on element not found
    @Override
    public void delete(long id, Connection conn) throws SQLException {
        String query = String.format("DELETE FROM \"%s\" WHERE class_id = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ps.executeUpdate();
    }

    public List<ClassDto> findAllByAccountId(long id, Connection conn) throws SQLException {
        List<ClassDto> classesList = new ArrayList<>();
        String query = String.format(
                "SELECT system_class.class_id, course_name, class_address, class_year, teacher_id FROM \"%s\" INNER JOIN \"account_class\" ON account_class.class_id = system_class.class_id WHERE account_class.account_id = ?",
                TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            classesList.add(getDtoTableData(rs));
        }
        return classesList;
    }

    public Optional<ClassDto> findSingleByAccountId(long accountId, long classId, Connection conn) throws SQLException {
        String query = String.format(
                "SELECT system_class.class_id, course_name, class_address, class_year, teacher_id FROM \"%s\" INNER JOIN \"account_class\" ON account_class.class_id = system_class.class_id WHERE account_class.account_id = ? and account_class.class_id = ?",
                TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(accountId, classId));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getDtoTableData(rs));
        }
        return Optional.empty();
    }

    private SystemClass getTableData(ResultSet rs) throws SQLException {
        try {
            return new SystemClass(
                    (Long) rs.getObject("class_id"),
                    rs.getString("course_name"),
                    rs.getString("class_address"),
                    (Integer) rs.getObject("class_year"),
                    (Long) rs.getObject("teacher_id"));
        } catch (NullPointerException e) {
            throw new IllegalStateException("Illegal values found", e);
        }
    }

    private ClassDto getDtoTableData(ResultSet rs) throws SQLException {
        try {
            return new ClassDto(
                    (Long) rs.getObject("class_id"),
                    rs.getString("course_name"),
                    rs.getString("class_address"),
                    (Integer) rs.getObject("class_year"),
                    (Long) rs.getObject("teacher_id"));
        } catch (NullPointerException e) {
            throw new IllegalStateException("Illegal values found", e);
        }
    }
}
