package com.feis.eduhub.backend.features.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credentials {
    @NonNull
    private Long credentialsId;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private Long updatedAt;
    @NonNull
    private Long accountId;

    public Credentials(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    public Credentials(@NonNull String email, @NonNull String password, @NonNull Long updatedAt) {
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
    }

    public Credentials(@NonNull String email, @NonNull String password, @NonNull Long updatedAt,
            @NonNull Long accountId) {
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
        this.accountId = accountId;
    }
}
