package com.feis.eduhub.backend.features.systemNotice.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.feis.eduhub.backend.common.config.DatabaseConnection;
import com.feis.eduhub.backend.common.config.MailSender;
import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.common.exceptions.DatabaseQueryException;
import com.feis.eduhub.backend.common.exceptions.EmailSendingException;
import com.feis.eduhub.backend.features.systemNotice.SystemNotice;
import com.feis.eduhub.backend.features.systemNotice.dao.SystemNoticeDao;
import com.feis.eduhub.backend.features.systemNotice.template.NoticeTemplate;

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

    /**
     * Sends a notice to the specified user user via email through
     * the {@code MailSender} class.
     *
     * @param recipient The email address of the recipient
     * @param message   The content of the notice to be sent
     * @throws EmailSendingException If there is an error during the email sending
     *                               process
     * 
     * @see MailSender
     * @see NoticeTemplate
     */
    public void sendNoticeViaEmail(String recipient, String message) throws EmailSendingException {
        MailSender mailSender = MailSender.getInstance("EduHub Mailing System");
        String fullHtmlMessage = NoticeTemplate.renderHtmlNotice(message);
        mailSender.deliverMessage(recipient, "There's a message for you.", "Hello",
                fullHtmlMessage, false);
    }
}
