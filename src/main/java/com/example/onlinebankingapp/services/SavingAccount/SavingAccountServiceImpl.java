package com.example.onlinebankingapp.services.SavingAccount;

import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingAccountServiceImpl implements SavingAccountService {
    @Override
    public SavingAccountEntity insertSavingAccount(SavingAccountDTO savingAccountDTO) {
        return null;
    }

    @Override
    public SavingAccountEntity getSavingAccountById(Long id) throws Exception {
        return null;
    }
}
