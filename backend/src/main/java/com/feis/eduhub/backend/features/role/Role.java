package com.feis.eduhub.backend.features.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    private long role_id;
    @NonNull
    private String role_name;

    public Role(String role_name) {
        this.role_name = role_name;
    }
}
