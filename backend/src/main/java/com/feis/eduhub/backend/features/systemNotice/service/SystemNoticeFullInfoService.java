package com.feis.eduhub.backend.features.systemNotice.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DataFetchException;
import com.feis.eduhub.backend.common.exceptions.NotFoundException;
import com.feis.eduhub.backend.features.systemNotice.dao.SystemNoticeFullInfoDao;
import com.feis.eduhub.backend.features.systemNotice.dto.SystemNoticeFullInfoDto;

public class SystemNoticeFullInfoService {
    private final SystemNoticeFullInfoDao systemNoticeFullInfoDao;
    private final DatabaseConnection databaseConnection;

    public SystemNoticeFullInfoService() {
        systemNoticeFullInfoDao = new SystemNoticeFullInfoDao();
        databaseConnection = DatabaseConnection.getInstance();
    }

    public SystemNoticeFullInfoDto getNoticeById(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return systemNoticeFullInfoDao.findById(id, conn).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Notice not found", e);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<SystemNoticeFullInfoDto> getAllNotices() throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return systemNoticeFullInfoDao.getAll(conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<SystemNoticeFullInfoDto> getAllNoticesByRecipientId(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return systemNoticeFullInfoDao.getAllByRecipientId(id, conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }

    public List<SystemNoticeFullInfoDto> getAllNoticesBySenderId(long id) throws AppException {
        try (Connection conn = databaseConnection.getConnection()) {
            return systemNoticeFullInfoDao.getAllBySenderId(id, conn);
        } catch (SQLException e) {
            throw new DataFetchException("Could not fetch data", e);
        }
    }
}
