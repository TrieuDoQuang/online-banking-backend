package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="saving_accounts")
public class SavingAccountEntity extends AbstractAccount{
    @Id
    @Column(name="savingAccountId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingAccountId;

    @Column(name="minBalance", nullable = false)
    private int minBalance;

    @Column(name="savingCurrentAmount", nullable = false)
    private Double savingCurrentAmount;

    @Column(name="savingInitialAmount", nullable = false)
    private Double savingInitialAmount;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentAccountId", referencedColumnName = "paymentAccountId")
    private PaymentAccountEntity paymentAccount;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_rate_id", referencedColumnName = "interestId")
    private InterestRateEntity interestRate;
}
