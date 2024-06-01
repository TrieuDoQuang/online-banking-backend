package com.example.onlinebankingapp.services.SavingAccount;

import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.*;
import com.example.onlinebankingapp.enums.AccountStatus;
import com.example.onlinebankingapp.enums.AccountType;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.repositories.InterestRateRepository;
import com.example.onlinebankingapp.repositories.PaymentAccountRepository;
import com.example.onlinebankingapp.repositories.SavingAccountRepository;
import com.example.onlinebankingapp.services.Customer.CustomerService;
import com.example.onlinebankingapp.services.InterestRate.InterestRateService;
import com.example.onlinebankingapp.services.PaymentAccount.PaymentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SavingAccountServiceImpl implements SavingAccountService {
    private final SavingAccountRepository savingAccountRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final InterestRateRepository interestRateRepository;
    private final PaymentAccountService paymentAccountService;

    //insert a new saving account
    //in charge: khai
    @Override
    public SavingAccountEntity insertSavingAccount(SavingAccountDTO savingAccountDTO) throws Exception {
        //validate dto
        String dataValidationResult = isSavingAccountDTOValid(savingAccountDTO);
        if (!dataValidationResult.equals("OK")) {
            throw new DataIntegrityViolationException(dataValidationResult);
        }

        // Retrieve payment account and interest rate references
        PaymentAccountEntity paymentAccountReference = paymentAccountRepository.getReferenceById(savingAccountDTO.getPaymentAccountId());
        InterestRateEntity interestRateReference = interestRateRepository.getReferenceById(savingAccountDTO.getInterestRateId());

        // Check if the initial deposit amount meets the minimum balance requirement for the interest rate package
        if (savingAccountDTO.getSavingInitialAmount() < interestRateReference.getMinBalance()) {
            throw new DataIntegrityViolationException("The deposit amount is insufficient for this interest rate package");
        }

        // Create a new saving account entity
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

        // Deduct the corresponding amount from the payment account balance
        Double newPaymentAccountBalance = paymentAccountReference.getCurrentBalance() - newSavingAccountEntity.getSavingInitialAmount();
        if (newPaymentAccountBalance < 0) {
            throw new DataIntegrityViolationException("Payment account does not have enough balance for the required amount in saving acocunt");
        }
        paymentAccountReference.setCurrentBalance(newPaymentAccountBalance);

        // Save the updated payment account and the new saving account
        paymentAccountRepository.save(paymentAccountReference);
        return savingAccountRepository.save(newSavingAccountEntity);
    }

    //get saving account by id
    //in charge: khai
    @Override
    public SavingAccountEntity getSavingAccountById(Long id) throws Exception {
        if (id == null) {
            throw new Exception("Missing parameter");
        }

        // Retrieve the saving account by ID
        SavingAccountEntity querySavingAccount = savingAccountRepository.findSavingAccountEntityById(id);
        if (querySavingAccount != null) {
            return querySavingAccount;
        }
        throw new DataNotFoundException("Cannot find saving account with Id: " + id);
    }

    //get all saving accounts of a user
    //in charge: khai
    @Override
    public List<SavingAccountEntity> getSavingAccountsOfUser(Long userId) throws Exception {
        // Retrieve payment accounts for the user
        List<PaymentAccountEntity> userPaymentAccountsList = paymentAccountService.getPaymentAccountsByCustomerId(userId);
        List<SavingAccountEntity> userSavingAccountsList = new ArrayList<>();

        // For each payment account, find associated saving accounts
        for (PaymentAccountEntity paymentAccount : userPaymentAccountsList) {
            List<SavingAccountEntity> savingAccountsOfPaymentAccount
                    = savingAccountRepository.findSavingAccountEntitiesByPaymentAccount(paymentAccount);
            userSavingAccountsList.addAll(savingAccountsOfPaymentAccount);
        }

        return userSavingAccountsList;
    }

    @Override
    public List<SavingAccountEntity> getSavingAccountsOfPaymentAccount(Long paymentAccountId) throws Exception {
        return null;
    }

    //get all saving accounts
    //in charge: khai
    @Override
    public List<SavingAccountEntity> getAllSavingAccounts() throws Exception {
        return savingAccountRepository.findAll();
    }

    @Override
    public SavingAccountEntity updateSavingAccounts(Long id, SavingAccountDTO savingAccountDTO) throws Exception {
        return null;
    }

    //insert a new saving account
    //in charge: khai
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    @Override
    public SavingAccountEntity withdrawSavingAccount(Long id) throws Exception {
        SavingAccountEntity savingAccount = getSavingAccountById(id);

        // Check if the account is active
        if (!savingAccount.getAccountStatus().equals(AccountStatus.ACTIVE)) {
            throw new Exception("This saving account is inactive, cannot withdraw from an inactive account");
        }

        PaymentAccountEntity paymentAccount = savingAccount.getPaymentAccount();

        // Update account status and balance
        savingAccount.setAccountStatus(AccountStatus.INACTIVE);
        savingAccount.setDateClosed(new java.sql.Date(System.currentTimeMillis()));
        Double transferAmount = savingAccount.getSavingInitialAmount();
        savingAccount.setSavingCurrentAmount((double) 0);
        paymentAccount.setCurrentBalance(paymentAccount.getCurrentBalance() + transferAmount);
        paymentAccountRepository.save(paymentAccount);

        return savingAccountRepository.save(savingAccount);
    }

//    @Override
//    public void performSavingAccountsDailyUpdate() {
//        List<SavingAccountEntity> savingAccountEntityList = savingAccountRepository.findAll();
//
//        for (SavingAccountEntity savingAccount : savingAccountEntityList) {
//            if (savingAccount.getAccountStatus().equals(AccountStatus.ACTIVE)) {
//                if(isEndOfTerm(savingAccount)){
//                    deactiveAndTransferCurrentBalanceToPaymentAccount(savingAccount);
//                } else {
//                    updateDailyCurrentBalance(savingAccount);
//                }
//            }
//        }
//    }

    // Method to check if the saving account term has ended
    // in charge: Khai
    public boolean isEndOfTerm(SavingAccountEntity savingAccount) {
        // Get the term length in months from the interest rate of the account
        Integer termNo = savingAccount.getInterestRate().getTerm();
        // Convert the term length to days (assuming 30 days per month)
        int totalTermDays = termNo * 30;

        // Get the current date
        LocalDate todayLocalDate = LocalDate.now();
        // Get the date when the term started
        LocalDate startTermLocalDate = savingAccount.getDateOpened().toLocalDate();
        // Calculate the end date of the term
        LocalDate endTermLocalDate = startTermLocalDate.plusDays(totalTermDays);
        // Check if today is the end of the term or after the end date
        return (todayLocalDate.isEqual(endTermLocalDate) || todayLocalDate.isAfter(endTermLocalDate));
    }

    // Method to deactivate a saving account and transfer its balance to a payment account
    //in charge: khai
    @Override
    public SavingAccountEntity deactiveAndTransferCurrentBalanceToPaymentAccount(SavingAccountEntity savingAccount) {
        // Get the associated payment account
        PaymentAccountEntity associatedPaymentAccount = savingAccount.getPaymentAccount();
        // Get the current balance of the saving account
        Double transferAmount = savingAccount.getSavingCurrentAmount();
        // Calculate the reward points based on the initial amount (assuming 1 point per 10000 units)
        int transferRewardPoints = (int) (savingAccount.getSavingInitialAmount() / 10000);
        // Set the saving account balance to zero
        savingAccount.setSavingCurrentAmount((double) 0);
        // Transfer the balance and reward points to the payment account
        associatedPaymentAccount.setCurrentBalance(associatedPaymentAccount.getCurrentBalance() + transferAmount);
        associatedPaymentAccount.setRewardPoint(associatedPaymentAccount.getRewardPoint() + transferRewardPoints);
        // Save the updated payment account to the repository
        paymentAccountRepository.save(associatedPaymentAccount);
        // Set the saving account status to inactive and record the closing date
        savingAccount.setAccountStatus(AccountStatus.INACTIVE);
        savingAccount.setDateClosed(new java.sql.Date(System.currentTimeMillis()));
        // Save and return the updated saving account
        return savingAccountRepository.save(savingAccount);
    }

    // Method to update the daily current balance of a saving account
    //in charge: khai
    @Override
    public SavingAccountEntity updateDailyCurrentBalance(SavingAccountEntity savingAccount) {
        // Get the interest rate entity for the account
        InterestRateEntity accountInterestRate = savingAccount.getInterestRate();
        // Calculate the daily earned interest
        // Formula: daily earned amount = initial amount * (interest rate / 100) / 12 (months) / 30 (days)
        Double dailyEarnedInterest = savingAccount.getSavingInitialAmount() * (accountInterestRate.getInterestRate() / 100) / 12 / 30;
        // Update the current balance with the daily earned interest
        savingAccount.setSavingCurrentAmount(savingAccount.getSavingCurrentAmount() + dailyEarnedInterest);
        // Save and return the updated saving account
        return savingAccountRepository.save(savingAccount);
    }

    // Method to validate a saving account DTO
    //in charge: khai
    private String isSavingAccountDTOValid(SavingAccountDTO savingAccountDTO) {
        // Get the initial amount and account type from the DTO
        Double savingInitialAmount = savingAccountDTO.getSavingInitialAmount();
        String accountType = savingAccountDTO.getAccountType();

        // Validate that the initial amount is greater than 0
        if (savingInitialAmount <= 0) {
            return "Số tiền gửi phải lớn hơn 0";
        }

        // Validate that the account type is a valid enum value
        try {
            AccountType.valueOf(accountType);
        } catch (Exception e) {
            return "Loại tài khoản không hợp lệ";
        }

        // If all validations pass, return "OK"
        return "OK";
    }

    // Method to generate a random saving account number
    //in charge: khai
    private String generateRandomSavingAccountNumber() {
        Random random = new Random();
        StringBuilder cardNumber;
        // Generate a unique account number that does not already exist in the repository
        do {
            cardNumber = new StringBuilder("SA"); // Prefix for saving account
            // Append 8 random digits to the prefix
            for (int i = 0; i < 8; i++) {
                int randomNum = random.nextInt(10); // Generate a random digit (0-9)
                cardNumber.append(Integer.toString(randomNum)); // Append the digit
            }
        } while (savingAccountRepository.existsByAccountNumberEquals(cardNumber.toString()));

        // Return the generated account number
        return cardNumber.toString();
    }
}
