package com.example.onlinebankingapp.entities;

import com.example.onlinebankingapp.entities.enums.AccountStatus;
import com.example.onlinebankingapp.entities.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="paymentAccounts")
public class PaymentAccountEntity {
    @Id
    @Column(name = "paymentAccountId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentAccountId;

    @Column(name = "accountNumber", length = 10, nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountStatus", nullable = false)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountType", nullable = false)
    private AccountType accountType;

    @Column(name = "currentBalance", nullable = false)
    private Double currentBalance;

    @Column(name="dateClosed", nullable = false)
    private Date dateClosed;

    @Column(name="dateOpened", length = 20, nullable = false)
    private Date dateOpened;

    @Column(name = "rewardPoint", nullable = false)
    private Integer rewardPoint;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId", nullable = false)
    private CustomerEntity customer;

    @OneToOne(mappedBy = "paymentAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BeneficiaryEntity beneficiary;
}
