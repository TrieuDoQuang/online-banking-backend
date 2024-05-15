package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="employee")
public class EmployeeEntity {

    @Id
    @Column(name="employeeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name="address", length = 100, nullable = false)
    private String address;

    @Column(name="citizenId", length = 20, nullable = false, unique = true)
    private String citizenId;

    @Column(name="dateOfBirth", length = 20, nullable = false)
    private Date dateofbirth;

    @Column(name="email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name="name", length = 100, nullable = false)
    private String name;

    @Column(name="password", length = 100, nullable = false)
    private String password;

    @Column(name="phonenumber", length = 20, nullable = false)
    private String phonenumber;

    @Enumerated(EnumType.STRING)
    @Column(name="role", length = 20, nullable = false)
    private Role role;
}

