package com.feis.eduhub.backend.common.rbac;

import java.util.Map;
import java.util.Set;

import com.feis.eduhub.backend.common.exceptions.AppException;
import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.account.service.AccountService;

/**
 * Utility class for role-based access control functionality.
 * 
 * In order for this to work, the class stores a hardcoded {@code Map}
 * containing all assignable roles within the app (defined by the
 * {@link UserRole} enum) and their relative database IDs as the map's key for
 * quick lookup. The map stores the following
 * - Role ID 1 represents ADMIN
 * - Role ID 2 represents TEACHER
 * - Role ID 3 represents STUDENT
 * 
 * @see UserRole
 * @see AppAction
 */
public class Rbac {
    private final Map<Long, UserRole> userRolesMap = Map.of(1L, UserRole.ADMIN, 2L, UserRole.TEACHER, 3L,
            UserRole.STUDENT);
    private final AccountService accountService;

    public Rbac() {
        accountService = new AccountService();
    }

    /**
     * Checks if a user with the given role ID has permission to perform the
     * specified action.
     *
     * @param roleId          The ID of the user's role
     * @param actionToExecute The action that needs to be checked for permission
     * @return true if the user has permission to perform the action (or has the
     *         "EVERYTHING" permission value), false
     *         otherwise.
     */
    public boolean checkPermission(Long roleId, AppAction actionToExecute) {
        Set<AppAction> userRolePermittedActions = userRolesMap.get(roleId).getPermittedActions();
        return userRolePermittedActions.contains(AppAction.EVERYTHING)
                || userRolePermittedActions.contains(actionToExecute);
    }

    /**
     * Alternative to {@code checkPermission} method with role obtaining from
     * persistence (e.g. Database/Redis).
     *
     * @param accountId       The Database {@code accountId}
     * @param actionToExecute The action that needs to be checked for permission
     * @return true if the user has permission to perform the action (or has the
     *         "EVERYTHING" permission value), false
     *         otherwise.
     */
    public boolean checkPermissionFromPersistence(Long accountId, AppAction actionToExecute)
            throws AppException {
        Account account = accountService.getAccountById(accountId);
        Set<AppAction> userRolePermittedActions = userRolesMap.get(account.getRoleId()).getPermittedActions();
        return userRolePermittedActions.contains(AppAction.EVERYTHING)
                || userRolePermittedActions.contains(actionToExecute);
    }
}
