package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="interest_rates")
public class InterestRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="interestRate", nullable = false)
    private Double interestRate;

    @Column(name="term", nullable = false)
    private Integer term;

    @Column(name="min_balance", nullable = false)
    private Double minBalance;

//    @OneToMany(mappedBy = "interestRate", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
//    private List<SavingAccountEntity> savingAccounts;
}
