package com.example.onlinebankingapp.services.SavingAccount;

import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;

import java.util.List;

//interface defining abstract functions for Saving Account Service
//in charge: khai
public interface SavingAccountService {
    // Insert a new saving account based on the provided saving account DTO
    SavingAccountEntity insertSavingAccount(SavingAccountDTO savingAccountDTO) throws Exception;

    // Retrieve a saving account by its ID
    SavingAccountEntity getSavingAccountById(Long id) throws Exception;

    // Get all saving accounts associated with a user based on the user ID
    List<SavingAccountEntity> getSavingAccountsOfUser(Long userId) throws Exception;

    // Get all saving accounts associated with a payment account based on the payment account ID
    List<SavingAccountEntity> getSavingAccountsOfPaymentAccount(Long paymentAccountId) throws Exception;

    // Get all saving accounts
    List<SavingAccountEntity> getAllSavingAccounts() throws Exception;

    // Update a saving account based on the provided ID and saving account DTO
    SavingAccountEntity updateSavingAccounts(Long id, SavingAccountDTO savingAccountDTO) throws Exception;

    // Withdraw funds from a saving account based on the provided ID
    SavingAccountEntity withdrawSavingAccount(Long id) throws Exception;

    // Deactivate a saving account and transfer its current balance to the associated payment account
    SavingAccountEntity deactiveAndTransferCurrentBalanceToPaymentAccount(SavingAccountEntity savingAccount);

    // Update the daily current balance of a saving account
    SavingAccountEntity updateDailyCurrentBalance(SavingAccountEntity savingAccount);

    // Check if it's the end of the term for a saving account
    boolean isEndOfTerm(SavingAccountEntity savingAccount);
}
