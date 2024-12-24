package com.feis.eduhub.backend.features.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @NonNull
    private Long accountId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String icon;
    @NonNull
    private Long roleId;

    // NOTE: DB NOT NULL fields constructor
    public Account(@NonNull String firstName, @NonNull String lastName, @NonNull Long roleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
    }

    // NOTE: All fields except auto-incremental ID constructor
    public Account(@NonNull String firstName, @NonNull String lastName, String icon, @NonNull Long roleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.icon = icon;
        this.roleId = roleId;
    }
}