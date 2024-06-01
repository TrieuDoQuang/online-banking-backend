package com.example.onlinebankingapp.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

//in charge: Dat + Khai
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // Indicates that the class is mapped to the database but not directly as an entity
@SuperBuilder // Provides a builder pattern for the class
public abstract class AbstractUser //abstract class for user
{
    @Column(name="email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name="name", length = 100, nullable = false)
    private String name;

    @Column(name="password", length = 100, nullable = false)
    private String password;

    @Column(name="phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name="address", length = 100, nullable = false)
    private String address;

    @Column(name="citizen_id", length = 20, nullable = false, unique = true)
    private String citizenId;

    @Column(name="date_of_birth", length = 20, nullable = false)
    private Date dateOfBirth;
}
