package com.example.onlinebankingapp.services.SavingAccount;

import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;

public interface SavingAccountService {
    SavingAccountEntity insertSavingAccount(SavingAccountDTO savingAccountDTO) throws Exception;

    SavingAccountEntity getSavingAccountById(Long id) throws Exception;

    SavingAccountEntity getAllSavingAccounts() throws Exception;

    SavingAccountEntity updateSavingAccounts(Long id, SavingAccountDTO savingAccountDTO) throws Exception;

}
