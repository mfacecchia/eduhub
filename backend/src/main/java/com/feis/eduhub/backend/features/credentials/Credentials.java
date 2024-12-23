package com.feis.eduhub.backend.features.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credentials {
    private long credentials_id;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private long updated_at;
    private long account_id;

    public Credentials(String email, String password, long updated_at) {
        this.email = email;
        this.password = password;
        this.updated_at = updated_at;
    }

    public Credentials(String email, String password, long updated_at, long account_id) {
        this.email = email;
        this.password = password;
        this.updated_at = updated_at;
        this.account_id = account_id;
    }
}
