package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccountEntity, Long> {
    boolean existsByAccountNumberEquals(String accountNumber);
}
