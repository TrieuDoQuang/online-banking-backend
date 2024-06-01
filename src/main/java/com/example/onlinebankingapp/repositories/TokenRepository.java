package com.example.onlinebankingapp.repositories;

import com.example.onlinebankingapp.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlinebankingapp.entities.CustomerEntity;

import java.util.List;
//in charge: trieu
//This repository interface provides methods for performing CRUD operations and querying token entities in the database.
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    // Find tokens associated with a specific customer
    List<TokenEntity> findByCustomer(CustomerEntity customer);
    // Find a token by its token string
    TokenEntity findByToken(String token);
    // Find a token by its refresh token string
    TokenEntity findByRefreshToken(String refreshToken);
}
