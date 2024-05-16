package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="rewards_of_accounts")
public class AccountRewardEntity {
    @Id
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "rewardId", referencedColumnName = "rewardId", nullable = false)
    private RewardEntity reward;

    @Id
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "accountId", referencedColumnName = "paymentAccountId", nullable = false)
    private PaymentAccountEntity paymentAccount;

    @Column(name = "isValid", nullable = false)
    private boolean isValid;
}
