package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

//in charge: Khai + Trieu
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="beneficiaries") // Specifies the name of the table in the database
public class BeneficiaryEntity //beneficiary table
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key of the entity

    @Column(name="name", length = 100, nullable = false) // Maps 'name' field to a column named "name"
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH) // Represents a many-to-one relationship with CustomerEntity
    @JoinColumn(name = "customer_id") // Specifies foreign key column in 'beneficiaries' table referencing 'id' in 'CustomerEntity'
    private CustomerEntity customer;

    @OneToOne(fetch = FetchType.LAZY) // Denotes one-to-one relationship with PaymentAccountEntity
    @JoinColumn(name = "payment_account_id") // Specifies foreign key column in 'beneficiaries' table referencing 'id' in 'PaymentAccountEntity'
    private PaymentAccountEntity paymentAccount;
}
