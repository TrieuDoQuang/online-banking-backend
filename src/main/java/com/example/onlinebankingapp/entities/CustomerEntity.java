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
@Table(name ="customers")
public class CustomerEntity extends AbstractUser{
    @Id
    @Column(name="customerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name="pinNumber", length = 6)
    private Integer pinNumber;
}
