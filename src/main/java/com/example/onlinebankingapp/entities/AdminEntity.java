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
@Table(name ="admins")
public class AdminEntity extends AbstractUser{
    @Id
    @Column(name="adminId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
}
