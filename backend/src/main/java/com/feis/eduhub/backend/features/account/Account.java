package com.feis.eduhub.backend.features.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    private long account_id;
    @NonNull
    private String first_name;
    @NonNull
    private String last_name;
    private String icon;
    private int role_id;

    public Account(String first_name, String last_name, int role_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.role_id = role_id;
    }

    public Account(String firstName, String lastName, String icon, int roleId) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.icon = icon;
        this.role_id = roleId;
    }
}