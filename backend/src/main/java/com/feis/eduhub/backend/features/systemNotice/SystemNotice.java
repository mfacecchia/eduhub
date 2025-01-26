package com.feis.eduhub.backend.features.systemNotice;

import com.feis.eduhub.backend.features.account.Account;

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
public class SystemNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    private String noticeMessage;

    @OneToOne
    private Account recipient;
    @OneToOne
    private Account sender;
}
