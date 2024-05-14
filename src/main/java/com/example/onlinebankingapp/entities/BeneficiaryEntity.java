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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "accountnumber")
//    private String accountNumber;
//
//    @Column(name = "cus_id")
//    private Long cus_id;
}
