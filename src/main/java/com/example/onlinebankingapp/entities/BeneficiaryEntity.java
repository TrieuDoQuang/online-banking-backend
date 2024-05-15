package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="beneficiaries")
public class BeneficiaryEntity {

    @Id
    @Column(name = "beneficiaryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beneficiaryId;

    @Column(name="name", length = 100, nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    private CustomerEntity customer;

    @Column(name="accountNumber", length = 20, nullable = false)
    private String accountNumber;
}
