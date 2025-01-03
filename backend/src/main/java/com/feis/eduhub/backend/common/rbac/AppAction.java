package com.feis.eduhub.backend.common.rbac;

/**
 * Enum representing all possible user actions within the app.
 * The constant {@code EVERYTHING} represents the possibility for a service
 * administrator to execute all actions
 */
public enum AppAction {
    EVERYTHING,
    MANAGE_QUIZZES,
    ASSESS_QUIZZES,
    ANSWER_QUIZZES,
    MANAGE_ACCOUNTS,
    MANAGE_CLASSES,
    MANAGE_LESSONS,
    MANAGE_NOTICES,
    MANAGE_CLASS_MEMBERS,
}
