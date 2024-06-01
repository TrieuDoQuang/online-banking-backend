package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//in charge: khai
//This repository interface provides methods for performing CRUD operations and querying saving account entities in the database.
@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccountEntity, Long> {
    // Check if a saving account with the specified account number exists
    boolean existsByAccountNumberEquals(String accountNumber);
    // Find a saving account entity by its ID
    SavingAccountEntity findSavingAccountEntityById(Long id);
    // Find saving account entities associated with a specific payment account
    List<SavingAccountEntity> findSavingAccountEntitiesByPaymentAccount(PaymentAccountEntity paymentAccount);
}
