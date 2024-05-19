package com.example.onlinebankingapp.repositories;

import com.example.onlinebankingapp.enums.AccountStatus;
import com.example.onlinebankingapp.enums.AccountType;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccountEntity, Long> {

    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PaymentAccountEntity p WHERE p.customer.id = :customerId AND p.accountStatus = :accountStatus")
    boolean checkExistingByStatus(long customerId, AccountStatus accountStatus);
    @Query("SELECT p FROM PaymentAccountEntity p WHERE p.accountStatus = :accountStatus and p.customer.id = :customerId")
    Optional<PaymentAccountEntity> getPaymentAccountByStatus(long customerId, AccountStatus accountStatus);

    PaymentAccountEntity getPaymentAccountByAccountNumber(String AccountNumber);
}
