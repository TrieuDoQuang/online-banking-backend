package com.example.onlinebankingapp.services.SavingAccount;

import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.SavingAccountEntity;

public interface SavingAccountService {
    SavingAccountEntity insertSavingAccount(SavingAccountDTO savingAccountDTO);

    SavingAccountEntity getSavingAccountById(Long id) throws Exception;
}
