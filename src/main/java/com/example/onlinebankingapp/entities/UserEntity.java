package com.example.onlinebankingapp.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column (name ="username")
//    private String username;
//
//    @Column (name ="password")
//    private String password;
//
//    @Column (name ="date_of_birth")
//    private Date dateOfBirth;
//
//    @Column (name ="email")
//    private String email;
//
//    private String name;
//
//    @Column (name = "phone_number")
//    private long phoneNumber;
//
//    @Column (name = "balance")
//    private Double balance;



}
