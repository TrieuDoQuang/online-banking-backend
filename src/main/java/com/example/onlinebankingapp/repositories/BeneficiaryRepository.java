package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.BeneficiaryEntity;
import com.example.onlinebankingapp.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
//in charge: dat
//This repository interface defines methods for interacting with the BeneficiaryEntity objects in the database.
@Repository
public interface BeneficiaryRepository extends JpaRepository<BeneficiaryEntity, Long> {

    // Method to find a beneficiary by customer ID and account number
    @Query("SELECT b FROM BeneficiaryEntity b WHERE b.customer.id = :customerId AND b.paymentAccount.accountNumber = :accountNumber")
    BeneficiaryEntity findByCustomerIdAndAccountNumber(long customerId, String accountNumber);

    // Method to find all beneficiaries by customer ID
    @Query("SELECT b FROM BeneficiaryEntity b WHERE b.customer.id = :customerId")
    List<BeneficiaryEntity> findByCustomerId(long customerId);

}
