package com.example.onlinebankingapp.entities;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_balance", nullable = false)
    private Double currentBalance;

    @Column(name = "reward_point", nullable = false)
    private Integer rewardPoint;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;


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
