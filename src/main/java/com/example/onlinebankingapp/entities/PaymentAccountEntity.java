package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

//in charge: khai + dat
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="payment_accounts") // Specifies the name of the table in the database
public class PaymentAccountEntity extends AbstractAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key of the entity

    @Column(name = "current_balance", nullable = false)
    private Double currentBalance; // Represents the current balance of the payment account

    @Column(name = "reward_point", nullable = false)
    private Integer rewardPoint; // Represents the reward points associated with the payment account

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer; // Represents the customer associated with the payment account

    // The onCreate method initializes the current balance and reward points when creating a new payment account
    protected void onCreate() {
        super.onCreate(); // Calls the onCreate method of the super class
        currentBalance = (double) 0; // Sets the current balance to 0
        rewardPoint = 0; // Sets the reward points to 0
    }

//    @OneToMany(mappedBy = "paymentAccount", cascade = CascadeType.ALL)
//    private List<SavingAccountEntity> savingAccounts;

    //    @ManyToMany(cascade = CascadeType.DETACH)
//    @JoinTable(
//            name = "rewards_of_accounts",
//            joinColumns = @JoinColumn(name = "accountId", referencedColumnName = "paymentAccountId"),
//            inverseJoinColumns = @JoinColumn(name = "rewardId", referencedColumnName = "rewardId")
//    )
//    private List<RewardEntity> rewards;

//    @OneToMany(mappedBy = "sender", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
//    private List<TransactionEntity> sentTransactions;
//
//    @OneToMany(mappedBy = "receiver", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
//    private List<TransactionEntity> receivedTransactions;
}
