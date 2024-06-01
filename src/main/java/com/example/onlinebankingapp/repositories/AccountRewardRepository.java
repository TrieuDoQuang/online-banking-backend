package com.example.onlinebankingapp.repositories;

import com.example.onlinebankingapp.entities.AccountRewardEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRewardRepository extends JpaRepository<AccountRewardEntity, AccountRewardEntity.AccountRewardRelationshipKey> {
    List<AccountRewardEntity> findAccountRewardEntitiesByAccountRewardKeyPaymentAccount(PaymentAccountEntity paymentAccount)
;}
