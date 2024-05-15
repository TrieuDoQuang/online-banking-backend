package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    @Column(name="address", nullable = false, length = 100)
    private String address;

    @Column(name="citizenId", nullable = false, length = 20)
    private String citizenId;

    @Column(name="dateOfBirth", nullable = false, length = 20)
    private String dateOfBirth;

    @Column(name="email", nullable = false, length = 100)
    private String email;

    @Column(name="name", nullable = false, length = 100)
    private String name;

    @Column(name="password", nullable = false, length = 100)
    private String password;

    @Column(name="phonenumber", nullable = false, length = 20)
    private String phonenumber;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false, length = 20)
    private Role role;
}

