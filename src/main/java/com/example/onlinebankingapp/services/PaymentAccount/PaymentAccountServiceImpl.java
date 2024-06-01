package com.example.onlinebankingapp.services.PaymentAccount;

import com.example.onlinebankingapp.dtos.AmountOperationDTO;
import com.example.onlinebankingapp.dtos.PaymentAccountDTO;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.enums.AccountStatus;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.repositories.CustomerRepository;
import com.example.onlinebankingapp.repositories.PaymentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentAccountServiceImpl implements PaymentAccountService {
    // Repositories for accessing payment account and customer data
    private final PaymentAccountRepository paymentAccountRepository;

    private final CustomerRepository customerRepository;

    // Method to insert a new payment account
    //in charge: dat
    @Override
    public PaymentAccountEntity insertPaymentAccount(PaymentAccountDTO paymentAccountDTO) throws DataNotFoundException {
        // Check if the account number already exists
        String accountNumber = paymentAccountDTO.getAccountNumber();
        if(paymentAccountRepository.existsByAccountNumber(accountNumber)){
            throw new DataIntegrityViolationException("The Account Number already exists");
        }

        // Create a new PaymentAccountEntity object
        PaymentAccountEntity newPaymentAccount = PaymentAccountEntity.builder()
                .accountNumber(accountNumber)
                .currentBalance(paymentAccountDTO.getCurrentBalance())
                .build();

        long customerId = paymentAccountDTO.getCustomerId();

        // Check if the customer has an existing default account
        boolean isExistingDefaultAccount = paymentAccountRepository.checkExistingByStatus(customerId, AccountStatus.DEFAULT);

        // If no default account exists, set the new account as default
        if(!isExistingDefaultAccount) {
            newPaymentAccount.setAccountStatus(AccountStatus.DEFAULT);
        }

        // Set the customer reference for the new account
        CustomerEntity customer = customerRepository.getReferenceById(customerId);

        newPaymentAccount.setCustomer(customer);

        // Save and return the new payment account entity
        return paymentAccountRepository.save(newPaymentAccount);

    }

    // Method to set an account as the default payment account
    public void setDefaultPaymentAccount(long customerId, String accountNumber) throws DataNotFoundException {

        // Get the current default payment account and set its status to ACTIVE
        PaymentAccountEntity existingDefaultPaymentAccount = getDefaultPaymentAccount(customerId);

        existingDefaultPaymentAccount.setAccountStatus(AccountStatus.ACTIVE);

        // Get the payment account to be set as default and set its status to DEFAULT
        PaymentAccountEntity paymentAccountToSetDefault = paymentAccountRepository.getPaymentAccountByAccountNumber(accountNumber);

        paymentAccountToSetDefault.setAccountStatus(AccountStatus.DEFAULT);

        // Save the updated entities
        paymentAccountRepository.save(existingDefaultPaymentAccount);

        paymentAccountRepository.save(paymentAccountToSetDefault);
    }

    // Method to get all payment accounts
    @Override
    public List<PaymentAccountEntity> getAllPaymentAccounts() throws DataNotFoundException {
        return paymentAccountRepository.findAll();
    }

    // Method to get a payment account by its ID
    @Override
    public PaymentAccountEntity getPaymentAccountById(long id) throws DataNotFoundException {
        //get the payment account by its id
        Optional<PaymentAccountEntity> optionalPaymentAccount = paymentAccountRepository.findById(id);
        if(optionalPaymentAccount.isPresent()) {
            return optionalPaymentAccount.get();
        }
        throw new DataNotFoundException("Cannot find Payment Account with Id: " + id);
    }

    // Method to get a payment account by its account number
    @Override
    public PaymentAccountEntity getPaymentAccountByAccountNumber(String accountNumber) throws DataNotFoundException {
        //get the payment account by the account number
        PaymentAccountEntity paymentAccount = paymentAccountRepository.getPaymentAccountByAccountNumber(accountNumber);
        if(paymentAccount != null) {
            return paymentAccount;
        }
        throw new DataNotFoundException("Cannot find Payment Account with account Number: " + accountNumber);
    }

    // Method to get the default payment account for a customer
    @Override
    public PaymentAccountEntity getDefaultPaymentAccount(long customerId) throws DataNotFoundException {
        //find the default account of a customer
        Optional<PaymentAccountEntity> optionalPaymentAccount = paymentAccountRepository.getPaymentAccountByStatus(customerId, AccountStatus.DEFAULT);
        if(optionalPaymentAccount.isPresent()) {
            return optionalPaymentAccount.get();
        }
        throw new DataNotFoundException("Cannot find Default Payment Account");
    }

    // Method to get all payment accounts for a specific customer
    @Override
    public List<PaymentAccountEntity> getPaymentAccountsByCustomerId(long customerId) throws DataNotFoundException {
        //find all payment accounts of a customer
        List<PaymentAccountEntity> paymentAccounts = paymentAccountRepository.getPaymentAccountsByCustomerId(customerId);
        if (paymentAccounts.isEmpty()) {
            throw new DataNotFoundException("Cannot find Payment Accounts of Customer");
        }
        return paymentAccounts;
    }

    // Method to top up a payment account
    @Override
    public void topUpPaymentAccount(long paymentAccountId, AmountOperationDTO amountDTO) throws DataNotFoundException {
        //find and check if payment account exists
        Optional<PaymentAccountEntity> existingPaymentAccountOptional = paymentAccountRepository.findById(paymentAccountId);
        if (existingPaymentAccountOptional.isEmpty()) {
            throw new DataNotFoundException("Payment Account not found");
        }

        //update new balance in payment account
        PaymentAccountEntity existingPaymentAccount = existingPaymentAccountOptional.get();
        double amount = amountDTO.getAmount();
        double currentBalance = existingPaymentAccount.getCurrentBalance();
        existingPaymentAccount.setCurrentBalance(currentBalance + amount);
        paymentAccountRepository.save(existingPaymentAccount);
    }


    // Method to withdraw from a payment account
    @Override
    public void withdrawPaymentAccount(long paymentAccountId, AmountOperationDTO amountDTO) throws DataNotFoundException {
        //find and check if the payment account exists
        Optional<PaymentAccountEntity> existingPaymentAccountWithdraw = paymentAccountRepository.findById(paymentAccountId);
        if (existingPaymentAccountWithdraw.isEmpty()) {
            throw new DataNotFoundException("Payment Account not found");
        }

        //check if account has enough money to withdraw
        double amount = amountDTO.getAmount();
        PaymentAccountEntity existingPaymentAccount = existingPaymentAccountWithdraw.get();
        double currentBalance = existingPaymentAccount.getCurrentBalance();
        if (currentBalance < amount) {
            throw new DataNotFoundException("Insufficient balance for withdrawing");
        }

        //update new balance in account and save in db
        existingPaymentAccount.setCurrentBalance(currentBalance - amount);
        paymentAccountRepository.save(existingPaymentAccount);
    }
}
