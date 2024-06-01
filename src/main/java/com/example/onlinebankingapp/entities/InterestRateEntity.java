package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

//in charge: khai
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="interest_rates") // Specifies the name of the table in the database
public class InterestRateEntity //interest rate table
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key of the entity

    @Column(name="interestRate", nullable = false)
    private Double interestRate; // Represents the interest rate

    @Column(name="term", nullable = false)
    private Integer term; // Represents the term of the interest rate

    @Column(name="min_balance", nullable = false)
    private Double minBalance; // Represents the minimum balance required for the interest rate
}
