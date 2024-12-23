package com.feis.eduhub.backend.features.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    private long accountId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String icon;
    private long roleId;

    public Account(String firstName, String lastName, long roleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
    }

    public Account(String firstName, String lastName, String icon, long roleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.icon = icon;
        this.roleId = roleId;
    }
}