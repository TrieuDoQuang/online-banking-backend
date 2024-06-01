package com.example.onlinebankingapp.schedulers;

import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.enums.AccountStatus;
import com.example.onlinebankingapp.repositories.InterestRateRepository;
import com.example.onlinebankingapp.repositories.PaymentAccountRepository;
import com.example.onlinebankingapp.repositories.SavingAccountRepository;
import com.example.onlinebankingapp.services.PaymentAccount.PaymentAccountService;
import com.example.onlinebankingapp.services.SavingAccount.SavingAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Component for scheduling tasks related to saving accounts
// in charge: khai
@Component
@Slf4j
public class SavingAccountScheduler {
    // Saving Account Service dependency injection
    private final SavingAccountService savingAccountService;

    @Autowired
    public SavingAccountScheduler(SavingAccountService savingAccountService) {
        this.savingAccountService = savingAccountService;
    }

    // Scheduled task to perform daily update for saving accounts
    //@Scheduled(cron = "0 */1 * * * *", zone = "Asia/Saigon") //update every minute (for testing)
    @Scheduled(cron = "0 0 0 */1 * *", zone = "Asia/Saigon") //update at 0:00:00 everyday
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void performSavingAccountsDailyUpdate() throws Exception {
        log.info("Start daily saving account updates");

        // Retrieve all saving accounts
        List<SavingAccountEntity> savingAccountEntityList = savingAccountService.getAllSavingAccounts();

        // Iterate through each saving account
        for (SavingAccountEntity savingAccount : savingAccountEntityList) {
            // Check if the account is active
            if (savingAccount.getAccountStatus().equals(AccountStatus.ACTIVE)) {
                // If the account term has ended, deactivate it and transfer the balance to the associated payment account
                if(savingAccountService.isEndOfTerm(savingAccount)){
                    savingAccountService.deactiveAndTransferCurrentBalanceToPaymentAccount(savingAccount);
                } else {
                    // Otherwise, update the daily current balance
                    savingAccountService.updateDailyCurrentBalance(savingAccount);
                }
            }
        }
        log.info("End daily saving account updates");
    }
}
