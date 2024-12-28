package com.feis.eduhub.backend.common.lib;

import java.util.Map;

import com.feis.eduhub.backend.common.rbac.UserRole;

public final class UserRolesConstants {
    public static final Map<Long, UserRole> userRolesMap = Map.of(
            1L, UserRole.ADMIN,
            2L, UserRole.TEACHER,
            3L, UserRole.STUDENT);

    private UserRolesConstants() {
    }
}
