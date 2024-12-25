package com.feis.eduhub.backend.features.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SystemRole {
    @NonNull
    private Long roleId;
    @NonNull
    private String roleName;

    public SystemRole(@NonNull String roleName) {
        this.roleName = roleName;
    }
}
