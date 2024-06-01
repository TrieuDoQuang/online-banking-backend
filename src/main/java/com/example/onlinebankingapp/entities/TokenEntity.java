package com.example.onlinebankingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

//in charge: Trieu
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "tokens") // Specifies the name of the table in the database
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key of the entity

    @Column(name = "token", length = 255, nullable = false)
    private String token; // Represents the access token

    @Column(name = "refresh_token", length = 255, nullable = false)
    private String refreshToken; // Represents the refresh token

    @Column(name = "token_type", length = 50, nullable = false)
    private String tokenType; // Represents the type of token

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate; // Represents the expiration date of the token

    @Column(name = "refresh_expiration_date", nullable = false)
    private LocalDateTime refreshExpirationDate; // Represents the expiration date of the refresh token

    @Column(name = "is_mobile", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isMobile; // Indicates if the token is associated with a mobile device

    @Column(name = "revoked", nullable = false)
    private boolean revoked; // Indicates if the token has been revoked

    @Column(name = "expired", nullable = false)
    private boolean expired; // Indicates if the token has expired

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer; // Represents the associated customer
}
