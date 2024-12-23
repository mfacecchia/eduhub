package com.feis.eduhub.backend.features.user;

import com.feis.eduhub.backend.features.account.Account;
import com.feis.eduhub.backend.features.credentials.Credentials;
import com.feis.eduhub.backend.features.role.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class representing full user's information.
 * 
 * @param accountId Unique identifier for the user account
 * @param firstName User's first name
 * @param lastName  User's last name
 * @param email     User's email address
 * @param icon      Path or URL to user's profile icon/avatar
 * @param roleName  Name of the user's role in the system
 * @param roleId    Unique identifier for the user's role
 * 
 * @see Credentials
 * @see Account
 * @see Role
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long accountId;
    private String firstName;
    private String lastName;
    private String email;
    private String icon;
    private String roleName;
    private Long roleId;

    public UserDto(Long accountId, String firstName, String lastName, String email, String icon, String roleName) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.icon = icon;
        this.roleName = roleName;
    }

    public UserDto(Long accountId, String firstName, String lastName, String email, String icon, Long roleId) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.icon = icon;
        this.roleId = roleId;
    }
}
