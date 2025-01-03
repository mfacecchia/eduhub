package com.feis.eduhub.backend.features.systemNotice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.feis.eduhub.backend.common.lib.Sql;
import com.feis.eduhub.backend.features.systemNotice.SystemNotice;

public class SystemNoticeDao {
    private final String TABLE_NAME = "system_notice";

    public SystemNotice create(SystemNotice systemNotice, Connection conn) throws SQLException {
        String query = String.format(
                "INSERT INTO \"%s\" (notice_message, recipient_id, sender_id) VALUES (?, ?, ?)",
                TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        Sql.setParams(ps,
                Arrays.asList(systemNotice.getNoticeMessage(), systemNotice.getRecipientId(),
                        systemNotice.getSenderId()));
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Notice not created");
        }
        systemNotice.setNoticeId(generatedKeys.getLong("notice_id"));
        return systemNotice;
    }

    public void delete(long id, Connection conn) throws SQLException {
        String query = String.format("DELETE FROM \"%s\" WHERE notice_id = ?", TABLE_NAME);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ps.executeUpdate();
    }
}
