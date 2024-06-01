package com.example.onlinebankingapp.repositories;

import com.example.onlinebankingapp.entities.AccountRewardEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//in charge: khai
/*
* This repository interface defines methods for interacting
*  with the AccountRewardEntity objects in the database.*/
public interface AccountRewardRepository extends JpaRepository<AccountRewardEntity, AccountRewardEntity.AccountRewardRelationshipKey> {
    // Method to find account reward entities by payment account
    List<AccountRewardEntity> findAccountRewardEntitiesByAccountRewardKeyPaymentAccount(PaymentAccountEntity paymentAccount);
    // Method to check if an account reward entity exists by account reward key
    boolean existsAccountRewardEntityByAccountRewardKey(AccountRewardEntity.AccountRewardRelationshipKey accountRewardKey);
    // Method to find an account reward entity by account reward key
    AccountRewardEntity findAccountRewardEntityByAccountRewardKey(AccountRewardEntity.AccountRewardRelationshipKey accountRewardKey);
;}
