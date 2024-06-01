package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.BeneficiaryEntity;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//in charge: khai
//This repository interface defines methods for interacting with the InterestRateEntity objects in the database.
@Repository
public interface InterestRateRepository extends JpaRepository<InterestRateEntity, Long> {
    // Check if an interest rate exists by term, interest rate, and minimum balance
    boolean existsByTermEqualsAndInterestRateEqualsAndMinBalanceEquals(Integer term, Double interestRate, Double minBalance);

    // Find an interest rate entity by ID
    InterestRateEntity findInterestRateEntityById(Long id);
}
