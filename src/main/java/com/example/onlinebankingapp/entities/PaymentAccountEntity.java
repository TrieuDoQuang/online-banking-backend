package com.example.onlinebankingapp.entities;

import com.example.onlinebankingapp.entities.enums.AccountStatus;
import com.example.onlinebankingapp.entities.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="payment_accounts")
public class PaymentAccountEntity extends AbstractAccount{
    @Id
    @Column(name = "paymentAccountId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentAccountId;

    @Column(name = "currentBalance", nullable = false)
    private Double currentBalance;

    @Column(name = "rewardPoint", nullable = false)
    private Integer rewardPoint;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId", nullable = false)
    private CustomerEntity customer;

    @OneToOne(mappedBy = "paymentAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BeneficiaryEntity beneficiary;

    @OneToMany(mappedBy = "paymentAccount", cascade = CascadeType.ALL)
    private List<SavingAccountEntity> savingAccounts;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<TransactionEntity> sentTransactions;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<TransactionEntity> receivedTransactions;
}
