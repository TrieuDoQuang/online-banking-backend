package com.example.onlinebankingapp.entities;

import com.example.onlinebankingapp.entities.enums.AccountStatus;
import com.example.onlinebankingapp.entities.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class AbstractAccount {
    @Column(name = "accountNumber", length = 10, nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountStatus", nullable = false)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountType", nullable = false)
    private AccountType accountType;

    @Column(name="dateClosed", nullable = false)
    private Date dateClosed;

    @Column(name="dateOpened", nullable = false)
    private Date dateOpened;
}
