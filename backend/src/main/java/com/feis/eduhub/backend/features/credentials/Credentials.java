package com.feis.eduhub.backend.features.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credentials {
    private long credentialsId;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private long updatedAt;
    private long accountId;

    public Credentials(String email, String password, long updatedAt) {
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
    }

    public Credentials(String email, String password, long updatedAt, long accountId) {
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
        this.accountId = accountId;
    }
}
