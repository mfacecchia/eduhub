package com.feis.eduhub.backend.features.account.dto;

import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.credentials.Credentials;
import com.feis.eduhub.backend.features.role.SystemRole;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO class representing the relation between the {@code Account},
 * {@code Credentials}, and {@code Role} tables.
 * Used to retain all user information at once.
 * 
 * @see Account
 * @see Credentials
 * @see SystemRole
 */
@AllArgsConstructor
@Data
public class AccountFullInfoDto {
    private Long accountId;
    private String firstName;
    private String lastName;
    private String email;
    private String icon;
    private Long roleId;
    private String roleName;
}
