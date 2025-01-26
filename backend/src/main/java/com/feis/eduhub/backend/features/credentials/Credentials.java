package com.feis.eduhub.backend.features.credentials;

import java.time.Instant;

import com.feis.eduhub.backend.features.account.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialsId;
    @Column(unique = true)
    private String email;
    private String password;
    private Long updatedAt = Instant.now().getEpochSecond();

    @OneToOne
    private Account account;
}
