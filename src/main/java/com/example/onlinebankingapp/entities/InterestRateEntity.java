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
    @Column(name="interestId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestId;

    @Column(name="interestRate", nullable = false)
    private Double interestRate;

    @Column(name="loanTitle", length = 50, nullable = false)
    private String loanTitle;

    @Column(name="savingTitle", length = 50, nullable = false)
    private String savingTitle;

    @Column(name="term", nullable = false)
    private Integer term;

    @OneToMany(mappedBy = "interestRate", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<SavingAccountEntity> savingAccounts;
}
