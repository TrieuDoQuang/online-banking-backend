package com.example.onlinebankingapp.entities;

import com.example.onlinebankingapp.enums.RewardType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="rewards")
public class RewardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost_point", nullable = false)
    private Integer costPoint;

    @Column(name="reward_name", nullable = false)
    private String rewardName;

    @Enumerated(EnumType.STRING)
    @Column(name = "reward_type", length = 20, nullable = false)
    private RewardType rewardType;

    @Column(name = "image")
    private byte[] image;

//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "payment_account_id")
//    private PaymentAccountEntity paymentAccount;
}
