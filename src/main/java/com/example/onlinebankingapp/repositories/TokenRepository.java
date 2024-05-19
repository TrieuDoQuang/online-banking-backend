package com.example.onlinebankingapp.repositories;

import com.example.onlinebankingapp.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlinebankingapp.entities.CustomerEntity;

import java.util.List;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findByCustomer(CustomerEntity customer);
    TokenEntity findByToken(String token);
    TokenEntity findByRefreshToken(String token);
}
