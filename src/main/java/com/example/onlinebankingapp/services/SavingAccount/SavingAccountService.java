package com.example.onlinebankingapp.services.SavingAccount;

import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;

import java.util.List;

public interface SavingAccountService {
    SavingAccountEntity insertSavingAccount(SavingAccountDTO savingAccountDTO) throws Exception;

    SavingAccountEntity getSavingAccountById(Long id) throws Exception;

    List<SavingAccountEntity> getSavingAccountsOfUser(Long userId) throws Exception;

    List<SavingAccountEntity> getSavingAccountsOfPaymentAccount(Long paymentAccountId) throws Exception;

    List<SavingAccountEntity> getAllSavingAccounts() throws Exception;

    SavingAccountEntity updateSavingAccounts(Long id, SavingAccountDTO savingAccountDTO) throws Exception;

    SavingAccountEntity withdrawSavingAccount(Long id) throws Exception;

    SavingAccountEntity deactiveAndTransferCurrentBalanceToPaymentAccount(SavingAccountEntity savingAccount);
    SavingAccountEntity updateDailyCurrentBalance(SavingAccountEntity savingAccount);
    boolean isEndOfTerm(SavingAccountEntity savingAccount);
}
