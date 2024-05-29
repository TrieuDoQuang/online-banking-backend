package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccountEntity, Long> {
    boolean existsByAccountNumberEquals(String accountNumber);
    SavingAccountEntity findSavingAccountEntityById(Long id);
    List<SavingAccountEntity> findSavingAccountEntitiesByPaymentAccount(PaymentAccountEntity paymentAccount);
}
