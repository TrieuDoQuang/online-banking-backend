package com.example.onlinebankingapp.entities;

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
@Table(name ="saving_accounts") // Specifies the name of the table in the database
public class SavingAccountEntity extends AbstractAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key of the entity

    @Column(name="saving_current_amount", nullable = false)
    private Double savingCurrentAmount; // Represents the current amount saved in the account

    @Column(name="saving_initial_amount", nullable = false)
    private Double savingInitialAmount; // Represents the initial amount saved in the account

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_account_id")
    private PaymentAccountEntity paymentAccount; // Represents the associated payment account

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_rate_id")
    private InterestRateEntity interestRate; // Represents the associated interest rate
}
