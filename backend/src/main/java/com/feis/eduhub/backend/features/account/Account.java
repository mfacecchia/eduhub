package com.feis.eduhub.backend.features.account;

import java.util.Set;

import com.feis.eduhub.backend.features.role.SystemRole;
import com.feis.eduhub.backend.features.systemClass.SystemClass;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String firstName;
    private String lastName;
    private String icon;
    @ManyToOne
    private SystemRole role;
    @ManyToMany(mappedBy = "member")
    private Set<SystemClass> systemClass;
}