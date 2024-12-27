package com.feis.eduhub.backend.features.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccountDto {
    private Long accountId;
    private String firstName;
    private String lastName;
    private String icon;
    private Boolean attended;
}
