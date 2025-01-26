package com.feis.eduhub.backend.features.systemClass;

import java.util.Set;

import com.feis.eduhub.backend.features.account.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class SystemClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;
    private String courseName;
    private String classAddress;
    private Integer classYear;
    @ManyToOne
    private Account teacher;
    @ManyToMany
    @JoinTable(name = "class_member", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Account> member;
}
