package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
//in charge: dat
//This repository interface provides a method for querying transactions associated with a specific customer.
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    // Find transactions associated with a specific customer (as either sender or receiver)
    @Query("SELECT t FROM TransactionEntity t WHERE t.sender.customer.id = :customerId OR t.receiver.customer.id = :customerId")
    List<TransactionEntity> findTransactionsByCustomerId(long customerId);

}
