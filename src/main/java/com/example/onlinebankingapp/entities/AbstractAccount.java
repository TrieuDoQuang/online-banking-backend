package com.example.onlinebankingapp.entities;

import com.example.onlinebankingapp.enums.AccountStatus;
import com.example.onlinebankingapp.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAccount {

    @Column(name = "account_number", length = 10, nullable = false, unique = true)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name="dateClosed")
    private Date dateClosed;

    @Column(name="dateOpened")
    private Date dateOpened;;

    @PrePersist
    protected void onCreate() {

        if(this.accountStatus == null) {
            accountStatus = AccountStatus.ACTIVE;
        }
        if(this.accountType == null ) {
            accountType = AccountType.CLASSIC;
        }
        if (this.dateOpened == null) {
            this.dateOpened = Date.valueOf(LocalDate.now());
        }
        if (this.dateClosed == null) {
            this.dateClosed = Date.valueOf(LocalDate.now().plusYears(3));
        }
    }
}
