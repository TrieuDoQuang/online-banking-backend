package com.example.onlinebankingapp.entities;

import com.example.onlinebankingapp.enums.AccountStatus;
import com.example.onlinebankingapp.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

//in charge: 3 members
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="transactions") // Specifies the name of the table in the database
public class TransactionEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key of the entity

    @Column(name = "amount", nullable = false)
    private Double amount; // Represents the amount involved in the transaction

    @Column(name="transaction_date_time", nullable = false)
    private Timestamp transactionDateTime; // Represents the date and time of the transaction

    @Column(name = "transaction_remark", nullable = false)
    private String transactionRemark; // Represents any remarks associated with the transaction

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private PaymentAccountEntity sender; // Represents the sender of the transaction

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private PaymentAccountEntity receiver; // Represents the receiver of the transaction

    @PrePersist
    protected void onCreate() {
        transactionDateTime = Timestamp.from(Instant.now()); // Sets the transaction date time to the current time before persisting
    }


}
