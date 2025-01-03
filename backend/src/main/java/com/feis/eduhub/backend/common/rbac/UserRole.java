package com.feis.eduhub.backend.common.rbac;

import java.util.EnumSet;
import java.util.Set;

public enum UserRole {
    ADMIN(EnumSet.of(AppAction.EVERYTHING)),
    TEACHER(EnumSet.of(AppAction.MANAGE_QUIZZES, AppAction.ASSESS_QUIZZES,
            AppAction.MANAGE_NOTICES)),
    STUDENT(EnumSet.of(AppAction.ANSWER_QUIZZES));

    private final Set<AppAction> permittedActions;

    private UserRole(Set<AppAction> permittedActions) {
        this.permittedActions = permittedActions;
    }

    public Set<AppAction> getPermittedActions() {
        return permittedActions;
    }
}
