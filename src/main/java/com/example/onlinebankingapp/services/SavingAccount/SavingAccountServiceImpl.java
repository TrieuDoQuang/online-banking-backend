package com.example.onlinebankingapp.services.SavingAccount;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.enums.AccountStatus;
import com.example.onlinebankingapp.enums.AccountType;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.repositories.InterestRateRepository;
import com.example.onlinebankingapp.repositories.PaymentAccountRepository;
import com.example.onlinebankingapp.repositories.SavingAccountRepository;
import com.example.onlinebankingapp.services.InterestRate.InterestRateService;
import com.example.onlinebankingapp.services.PaymentAccount.PaymentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class SavingAccountServiceImpl implements SavingAccountService {
    private final SavingAccountRepository savingAccountRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final InterestRateRepository interestRateRepository;

    @Override
    public SavingAccountEntity insertSavingAccount(SavingAccountDTO savingAccountDTO) throws Exception {
        String dataValidationResult = isSavingAccountDTOValid(savingAccountDTO);
        if(!dataValidationResult.equals("OK")){
            throw new DataIntegrityViolationException(dataValidationResult);
        }

        SavingAccountEntity newSavingAccountEntity = SavingAccountEntity.builder()
                .savingInitialAmount(savingAccountDTO.getSavingInitialAmount())
                .savingCurrentAmount(savingAccountDTO.getSavingInitialAmount())
                .accountStatus(AccountStatus.ACTIVE)
                .accountType(AccountType.CLASSIC)
                .accountNumber(generateRandomSavingAccountNumber())
                .dateClosed(new java.sql.Date(System.currentTimeMillis()))
                .dateOpened(new java.sql.Date(System.currentTimeMillis()))
                .build();

        PaymentAccountEntity paymentAccountReference = paymentAccountRepository.getReferenceById(savingAccountDTO.getPaymentAccountId());
        InterestRateEntity interestRateReference = interestRateRepository.getReferenceById(savingAccountDTO.getInterestRateId());

        newSavingAccountEntity.setPaymentAccount(paymentAccountReference);
        newSavingAccountEntity.setInterestRate(interestRateReference);

        return savingAccountRepository.save(newSavingAccountEntity);
    }

    @Override
    public SavingAccountEntity getSavingAccountById(Long id) throws Exception {
        return null;
    }

    @Override
    public SavingAccountEntity getAllSavingAccounts() throws Exception {
        return null;
    }

    @Override
    public SavingAccountEntity updateSavingAccounts(Long id, SavingAccountDTO savingAccountDTO) throws Exception {
        return null;
    }

    private String isSavingAccountDTOValid(SavingAccountDTO savingAccountDTO){
        Double savingInitialAmount = savingAccountDTO.getSavingInitialAmount();
        String accountType = savingAccountDTO.getAccountType();

        if(savingInitialAmount <= 0){
            return "Số tiền gửi phải lớn hơn 0";
        }

        try {
            AccountType.valueOf(accountType);
        } catch (Exception e) {
            return "Loại tài khoản không hợp lệ";
        }

        return "OK";
    }

    private String generateRandomSavingAccountNumber(){
        Random random = new Random();
        StringBuilder cardNumber;
        do {
            cardNumber = new StringBuilder("SA");
            for(int i = 0; i < 8; i++){
                int randomNum = random.nextInt(10);
                cardNumber.append(Integer.toString(randomNum));
            }
        } while (savingAccountRepository.existsByAccountNumberEquals(cardNumber.toString()));

        return cardNumber.toString();
    }
}
