package com.feis.eduhub.backend.features.accountClass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClassMemberDto {
    private Long accountId;
    private String firstName;
    private String lastName;
    private String icon;
    private Long roleId;
}
