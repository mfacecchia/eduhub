package com.feis.eduhub.backend.features.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    private long roleId;
    @NonNull
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
