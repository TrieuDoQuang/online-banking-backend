package com.example.onlinebankingapp.entities;

import com.example.onlinebankingapp.enums.RewardType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

//in charge: khai
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="rewards") // Specifies the name of the table in the database
public class RewardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key of the entity

    @Column(name = "cost_point", nullable = false)
    private Integer costPoint; // Represents the cost in points required to redeem the reward

    @Column(name="reward_name", nullable = false)
    private String rewardName; // Represents the name of the reward

    @Enumerated(EnumType.STRING)
    @Column(name = "reward_type", length = 20, nullable = false)
    private RewardType rewardType; // Represents the type of the reward (e.g., discount, cashback)

    @Column(name = "image")
    private byte[] image; // Represents the image associated with the reward (the image bit map is stored in the db)

//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "payment_account_id")
//    private PaymentAccountEntity paymentAccount;
}
