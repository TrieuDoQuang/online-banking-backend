package com.example.onlinebankingapp.repositories;


import com.example.onlinebankingapp.entities.BeneficiaryEntity;
import com.example.onlinebankingapp.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<BeneficiaryEntity, Long> {

    @Query("SELECT b FROM BeneficiaryEntity b WHERE b.customer.id = :customerId AND b.paymentAccount.accountNumber = :accountNumber")
    BeneficiaryEntity findByCustomerIdAndAccountNumber(long customerId, String accountNumber);
    @Query("SELECT b FROM BeneficiaryEntity b WHERE b.customer.id = :customerId")
    List<BeneficiaryEntity> findByCustomerId(long customerId);

}
