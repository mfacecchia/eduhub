package com.feis.eduhub.backend.features.systemNotice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.feis.eduhub.backend.common.lib.Sql;
import com.feis.eduhub.backend.features.systemNotice.dto.SystemNoticeFullInfoDto;

public class SystemNoticeFullInfoDao {
    private final String DATABASE_FIELDS = "notice_id, notice_message, r.account_id as recipient_id, r.first_name as recipient_first_name, r.last_name as recipient_last_name, rc.email as recipient_email, s.account_id as sender_id, s.first_name as sender_first_name, s.last_name as sender_last_name";
    private final String JOIN_QUERIES = "INNER JOIN account as r ON r.account_id = system_notice.recipient_id INNER JOIN credentials as rc on r.account_id = rc.account_id INNER JOIN account as s ON s.account_id = system_notice.sender_id";

    public Optional<SystemNoticeFullInfoDto> findById(long id, Connection conn) throws SQLException {
        String query = String.format(
                "SELECT %s FROM system_notice %s WHERE notice_id = ?",
                DATABASE_FIELDS,
                JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return Optional.of(getTableData(rs));
        }
        return Optional.empty();
    }

    public List<SystemNoticeFullInfoDto> getAll(Connection conn) throws SQLException {
        List<SystemNoticeFullInfoDto> noticesList = new ArrayList<>();
        String query = String.format(
                "SELECT %s FROM system_notice %s",
                DATABASE_FIELDS,
                JOIN_QUERIES);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            noticesList.add(getTableData(rs));
        }
        return noticesList;
    }

    public List<SystemNoticeFullInfoDto> getAllByRecipientId(long id, Connection conn) throws SQLException {
        List<SystemNoticeFullInfoDto> noticesList = new ArrayList<>();
        String query = String.format(
                "SELECT %s FROM system_notice %s WHERE recipient_id = ?",
                DATABASE_FIELDS,
                JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            noticesList.add(getTableData(rs));
        }
        return noticesList;
    }

    public List<SystemNoticeFullInfoDto> getAllBySenderId(long id, Connection conn) throws SQLException {
        List<SystemNoticeFullInfoDto> noticesList = new ArrayList<>();
        String query = String.format(
                "SELECT %s FROM system_notice %s WHERE sender_id = ?",
                DATABASE_FIELDS,
                JOIN_QUERIES);
        PreparedStatement ps = conn.prepareStatement(query);
        Sql.setParams(ps, Arrays.asList(id));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            noticesList.add(getTableData(rs));
        }
        return noticesList;
    }

    private SystemNoticeFullInfoDto getTableData(ResultSet rs) throws SQLException {
        try {
            return new SystemNoticeFullInfoDto(
                    (Long) rs.getObject("notice_id"),
                    rs.getString("notice_message"),
                    (Long) rs.getObject("recipient_id"),
                    rs.getString("recipient_first_name"),
                    rs.getString("recipient_last_name"),
                    rs.getString("recipient_email"),
                    (Long) rs.getObject("sender_id"),
                    rs.getString("sender_first_name"),
                    rs.getString("sender_last_name"));
        } catch (NullPointerException e) {
            throw new IllegalStateException("Illegal values found", e);
        }
    }
}
