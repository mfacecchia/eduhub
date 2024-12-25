package com.feis.eduhub.backend.features.lesson;

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

public class LessonDao implements ModelDao<Lesson> {
    private final String TABLE_NAME = "lesson";

    @Override
    public Optional<Lesson> findById(long id, Connection conn) throws SQLException {
        String query = String.format("SELECT * FROM \"%s\" WHERE lesson_id = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    @Override
    public List<Lesson> getAll(Connection conn) throws SQLException {
        List<Lesson> lessonsList = new ArrayList<>();
        String query = String.format("SELECT * FROM \"%s\"", TABLE_NAME);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            lessonsList.add(getTableData(rs));
        }
        return lessonsList;
    }

    @Override
    public Lesson create(Lesson lesson, Connection conn) throws SQLException {
        String query = String.format(
                "INSERT INTO \"%s\" (lesson_date, starts_at, ends_at, room_no, account_id, class_id) VALUES (?, ?, ?, ?, ?, ?)",
                TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        Sql.setParams(ps, Arrays.asList(lesson.getLessonDate(), lesson.getStartsAt(), lesson.getEndsAt(),
                lesson.getRoomNo(), lesson.getCreatedById(), lesson.getClassId()));
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Lesson not created");
        }
        lesson.setLessonId(generatedKeys.getLong(1));
        return lesson;
    }

    @Override
    public void update(long id, Lesson lesson, Connection conn) throws SQLException {
        String query = String.format(
                "UPDATE \"%s\" SET lesson_date = COALESCE(?, lesson_date), starts_at = COALESCE(?, starts_at), ends_at = COALESCE(?, ends_at), room_no = COALESCE(?, room_no), account_id = COALESCE(?, account_id), class_id = COALESCE(?, class_id) WHERE lesson_id = ?",
                TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(lesson.getLessonDate(), lesson.getStartsAt(), lesson.getEndsAt(),
                lesson.getRoomNo(), lesson.getCreatedById(), lesson.getClassId(), id));
        ps.executeUpdate();
    }

    @Override
    public void delete(long id, Connection conn) throws SQLException {
        String query = String.format("DELETE FROM \"%s\" WHERE lesson_id = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ps.executeUpdate();
    }

    private Lesson getTableData(ResultSet rs) throws SQLException {
        try {
            return new Lesson(
                    (Long) rs.getObject("lesson_id"),
                    rs.getDate("lesson_date"),
                    rs.getTime("starts_at"),
                    rs.getTime("ends_at"),
                    (Integer) rs.getObject("room_no"),
                    (Long) rs.getObject("account_id"),
                    (Long) rs.getObject("class_id"));
        } catch (NullPointerException e) {
            throw new IllegalStateException("Illegal values found", e);
        }
    }
}
