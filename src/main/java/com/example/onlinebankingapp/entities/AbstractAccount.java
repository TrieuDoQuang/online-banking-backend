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

//in charge: Khai + Dat
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // Indicates that the class is mapped to the database but not directly as an entity
@SuperBuilder // Provides a builder pattern for the class
@EntityListeners(AuditingEntityListener.class) // Registers the AuditingEntityListener to automatically populate audit-related fields
public abstract class AbstractAccount //abstract class for account
{

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

        // Set default values for accountStatus, accountType, dateOpened, and dateClosed if they are null
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
