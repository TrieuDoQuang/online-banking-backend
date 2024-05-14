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
@Table(name ="employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name="address")
    private String address;

    @Column(name="citizenId")
    private String citizenId;

    @Column(name="dateOfBirth")
    private Date dateOfBirth;

    @Column(name="email")
    private String email;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

    @Column(name="phonenumber")
    private String phonenumber;

    @Column(name="role", columnDefinition = "varchar(255) check (role in ('Administrator', 'Staff'))")
    private String role;
}
