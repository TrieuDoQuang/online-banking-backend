package com.example.onlinebankingapp.repositories;

import com.example.onlinebankingapp.enums.AccountStatus;
import com.example.onlinebankingapp.enums.AccountType;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
//in charge: dat + trieu
//This repository interface provides methods for interacting with the PaymentAccountEntity objects in the database.
@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccountEntity, Long> {
    // Check if a payment account exists by account number
    boolean existsByAccountNumber(String accountNumber);
    // Check if a payment account exists by customer ID and account status
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PaymentAccountEntity p WHERE p.customer.id = :customerId AND p.accountStatus = :accountStatus")
    boolean checkExistingByStatus(long customerId, AccountStatus accountStatus);

    // Retrieve a payment account by customer ID and account status
    @Query("SELECT p FROM PaymentAccountEntity p WHERE p.accountStatus = :accountStatus and p.customer.id = :customerId")
    Optional<PaymentAccountEntity> getPaymentAccountByStatus(long customerId, AccountStatus accountStatus);

    // Retrieve payment accounts by customer ID
    @Query ("SELECT p FROM PaymentAccountEntity p WHERE p.customer.id = :customerId")
    List<PaymentAccountEntity> getPaymentAccountsByCustomerId(long customerId);

    // Retrieve a payment account by account number
    PaymentAccountEntity getPaymentAccountByAccountNumber(String AccountNumber);
}
