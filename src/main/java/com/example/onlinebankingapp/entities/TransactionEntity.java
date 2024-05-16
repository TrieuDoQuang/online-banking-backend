package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="transactions")
public class TransactionEntity {
    @Id
    @Column(name = "transactionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name="transactionDateTime", nullable = false)
    private Timestamp transactionDateTime;

    @Column(name = "transactionRemark", nullable = false)
    private String transactionRemark;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId", referencedColumnName = "paymentAccountId",nullable = false)
    private PaymentAccountEntity sender;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverId", referencedColumnName = "paymentAccountId",nullable = false)
    private PaymentAccountEntity receiver;
}
