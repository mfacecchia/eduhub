package com.feis.eduhub.backend.features.systemNotice.template;

import java.time.LocalDate;

/**
 * Utility class providing HTML templates for system notices.
 * 
 */
public class NoticeTemplate {
    private NoticeTemplate() {
    }

    /**
     * Renders a message into an HTML-formatted notice template.
     *
     * @param message The message content to be included in the notice
     * @return A String containing the complete HTML-formatted notice
     */
    public static String renderHtmlNotice(String message) {
        return "<section style=\"text-align: center; max-width: 600px; margin: 0 auto; font-family: Arial, sans-serif; padding: 20px;\">"
                + "<h1>New message from your school</h1>"
                + "</section>"
                + "<p>There's a new message for you from your school. Here's the content:</p>"
                + "<blockquote>\"" + message + "\"</blockquote>"
                + "<footer style=\"border: 1px solid #ccc; border-radius: 9px; padding: 15px; margin-top: 20px; background-color: #f8f8f8;\">"
                + "<p style=\"margin: 0; color: #666;\">&#169;Marco Facecchia " + LocalDate.now().getYear()
                + ". All rights reserved.</p>"
                + "<p style=\"margin: 0; color: #666;\">Please do not reply to this Email as it's not being monitored.</p>"
                + "</footer>";
    }
}
