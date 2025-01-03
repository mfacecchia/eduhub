package com.feis.eduhub.backend.features.systemNotice.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.features.systemNotice.SystemNotice;
import com.feis.eduhub.backend.features.systemNotice.dao.SystemNoticeDao;

public class SystemNoticeService {
    private final SystemNoticeDao systemNoticeDao;
    private final DatabaseConnection databaseConnection;

    public SystemNoticeService() {
        systemNoticeDao = new SystemNoticeDao();
        databaseConnection = DatabaseConnection.getInstance();
    }

    public SystemNotice createNotice(SystemNotice systemNotice) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            systemNoticeDao.create(systemNotice, conn);
            conn.commit();
            return systemNotice;
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while creating notice", e);
        }
    }

    public void deleteNotice(long noticeId) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            systemNoticeDao.delete(noticeId, conn);
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseQueryException("Error while deleting notice", e);
        }
    }
}
