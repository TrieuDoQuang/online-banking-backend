package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

//in charge: Khai
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="rewards_of_accounts")
public class AccountRewardEntity  //join table of payment account and reward, meaning the rewards that a payment account has
{
    @EmbeddedId // Indicates that the primary key is composed of fields from the embedded object
    private AccountRewardRelationshipKey accountRewardKey;

    @Column(name = "is_valid", nullable = false)
    @Builder.Default
    private boolean isValid = true;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountRewardRelationshipKey implements Serializable //composite primary key of AccountReward table
    {
        @ManyToOne(cascade = CascadeType.DETACH)
        @JoinColumn(name = "reward_id", nullable = false)
        private RewardEntity reward;

        @ManyToOne(cascade = CascadeType.DETACH)
        @JoinColumn(name = "payment_account_id", nullable = false)
        private PaymentAccountEntity paymentAccount;
    }
}
