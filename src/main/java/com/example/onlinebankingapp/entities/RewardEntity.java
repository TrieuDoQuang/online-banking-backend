package com.example.onlinebankingapp.entities;

import com.example.onlinebankingapp.entities.enums.AccountType;
import com.example.onlinebankingapp.entities.enums.RewardType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="rewards")
public class RewardEntity {
    @Id
    @Column(name="rewardId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rewardId;

    @Column(name = "costPoint", nullable = false)
    private Integer costPoint;

    @Column(name="rewardName", nullable = false)
    private String rewardName;

    @Enumerated(EnumType.STRING)
    @Column(name = "rewardType", length = 20, nullable = false)
    private RewardType rewardType;

//    @ManyToMany(mappedBy = "rewards", cascade = CascadeType.DETACH)
//    private List<PaymentAccountEntity> paymentAccounts;
}
