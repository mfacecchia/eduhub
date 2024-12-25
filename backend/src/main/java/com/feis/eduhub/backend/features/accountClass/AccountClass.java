package com.feis.eduhub.backend.features.accountClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Many-to-Many relation model between
 * {@link com.feis.eduhub.backend.features.account.Account Account} and
 * {@link com.feis.eduhub.backend.features.systemClass.SystemClass SystemClass}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountClass {
    @NonNull
    private Long accountId;
    @NonNull
    private Long classId;
}