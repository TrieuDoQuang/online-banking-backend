package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {


    @Query("SELECT t FROM TransactionEntity t WHERE t.sender.customer.id = :customerId OR t.receiver.customer.id = :customerId")
    List<TransactionEntity> findTransactionsByCustomerId(long customerId);

}
