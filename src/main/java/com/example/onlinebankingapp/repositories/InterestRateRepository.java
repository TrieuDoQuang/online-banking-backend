package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.BeneficiaryEntity;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRateEntity, Long> {
    boolean existsByTermEqualsAndInterestRateEquals(Integer term, Double interestRate);

    InterestRateEntity findInterestRateEntityById(Long id);
}
