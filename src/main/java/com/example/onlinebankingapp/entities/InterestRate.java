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
@Table(name ="interestRates")
public class InterestRate {
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
}
