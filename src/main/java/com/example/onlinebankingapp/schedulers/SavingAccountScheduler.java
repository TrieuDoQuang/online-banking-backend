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

@Component
@Slf4j
public class SavingAccountScheduler {
    private final SavingAccountService savingAccountService;

    @Autowired
    public SavingAccountScheduler(SavingAccountService savingAccountService) {
        this.savingAccountService = savingAccountService;
    }

    //@Scheduled(cron = "0 */1 * * * *", zone = "Asia/Saigon") //update every minute (for testing)
    @Scheduled(cron = "0 0 0 */1 * *", zone = "Asia/Saigon") //update at 0:00:00 everyday
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void performSavingAccountsDailyUpdate() throws Exception {
        log.info("Start daily saving account updates");
        List<SavingAccountEntity> savingAccountEntityList = savingAccountService.getAllSavingAccounts();

        for (SavingAccountEntity savingAccount : savingAccountEntityList) {
            if (savingAccount.getAccountStatus().equals(AccountStatus.ACTIVE)) {
                if(savingAccountService.isEndOfTerm(savingAccount)){
                    savingAccountService.deactiveAndTransferCurrentBalanceToPaymentAccount(savingAccount);
                } else {
                    savingAccountService.updateDailyCurrentBalance(savingAccount);
                }
            }
        }
        log.info("End daily saving account updates");
    }
}
