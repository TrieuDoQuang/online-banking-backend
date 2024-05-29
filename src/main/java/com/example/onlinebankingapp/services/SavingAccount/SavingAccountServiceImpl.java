package com.example.onlinebankingapp.services.SavingAccount;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SavingAccountServiceImpl implements SavingAccountService {
    private final SavingAccountRepository savingAccountRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final InterestRateRepository interestRateRepository;
    private final PaymentAccountService paymentAccountService;

    @Override
    public SavingAccountEntity insertSavingAccount(SavingAccountDTO savingAccountDTO) throws Exception {
        String dataValidationResult = isSavingAccountDTOValid(savingAccountDTO);
        if(!dataValidationResult.equals("OK")){
            throw new DataIntegrityViolationException(dataValidationResult);
        }

        PaymentAccountEntity paymentAccountReference = paymentAccountRepository.getReferenceById(savingAccountDTO.getPaymentAccountId());
        InterestRateEntity interestRateReference = interestRateRepository.getReferenceById(savingAccountDTO.getInterestRateId());

        //Kiểm tra tiền chuyển có đạt mức tối thiểu của interest rate
        if(savingAccountDTO.getSavingInitialAmount() < interestRateReference.getMinBalance()){
            throw new DataIntegrityViolationException("The deposit amount is insufficient for this interest rate package");
        }

        SavingAccountEntity newSavingAccountEntity = SavingAccountEntity.builder()
                .savingInitialAmount(savingAccountDTO.getSavingInitialAmount())
                .savingCurrentAmount(savingAccountDTO.getSavingInitialAmount())
                .accountStatus(AccountStatus.ACTIVE)
                .accountType(AccountType.CLASSIC)
                .accountNumber(generateRandomSavingAccountNumber())
                .dateClosed(new java.sql.Date(System.currentTimeMillis()))
                .dateOpened(new java.sql.Date(System.currentTimeMillis()))
                .paymentAccount(paymentAccountReference)
                .interestRate(interestRateReference)
                .build();

        //Giảm số tiền trong payment account tương ứng với tiền gửi của saving account
        Double newPaymentAccountBalance = paymentAccountReference.getCurrentBalance() - newSavingAccountEntity.getSavingInitialAmount();
        if(newPaymentAccountBalance < 0) {
            throw new DataIntegrityViolationException("Payment account does not have enough balance for the required amount in saving acocunt");
        }
        paymentAccountReference.setCurrentBalance(newPaymentAccountBalance);

        paymentAccountRepository.save(paymentAccountReference);
        return savingAccountRepository.save(newSavingAccountEntity);
    }

    @Override
    public SavingAccountEntity getSavingAccountById(Long id) throws Exception {
        if(id == null){
            throw new Exception("Missing parameter");
        }

        SavingAccountEntity querySavingAccount = savingAccountRepository.findSavingAccountEntityById(id);
        if(querySavingAccount != null) {
            return querySavingAccount;
        }
        throw new DataNotFoundException("Cannot find saving account with Id: "+ id);
    }

    @Override
    public List<SavingAccountEntity> getSavingAccountsOfUser(Long userId) throws Exception {
        return null;
    }

    @Override
    public List<SavingAccountEntity> getSavingAccountsOfPaymentAccount(Long paymentAccountId) throws Exception {
        return null;
    }

    @Override
    public List<SavingAccountEntity> getAllSavingAccounts() throws Exception {
        return savingAccountRepository.findAll();
    }

    @Override
    public SavingAccountEntity updateSavingAccounts(Long id, SavingAccountDTO savingAccountDTO) throws Exception {
        return null;
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    @Override
    public SavingAccountEntity withdrawSavingAccount(Long id) throws Exception {
        SavingAccountEntity savingAccount = getSavingAccountById(id);
        if(!savingAccount.getAccountStatus().equals(AccountStatus.ACTIVE)){
            throw new Exception("This saving account is inactive, cannot withdraw from an inactive account");
        }

        PaymentAccountEntity paymentAccount = savingAccount.getPaymentAccount();

        savingAccount.setAccountStatus(AccountStatus.INACTIVE);
        savingAccount.setDateClosed(new java.sql.Date(System.currentTimeMillis()));
        Double transferAmount = savingAccount.getSavingCurrentAmount();
        savingAccount.setSavingCurrentAmount((double) 0);
        paymentAccount.setCurrentBalance(paymentAccount.getCurrentBalance() + transferAmount);
        paymentAccountRepository.save(paymentAccount);

        return savingAccountRepository.save(savingAccount);
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
