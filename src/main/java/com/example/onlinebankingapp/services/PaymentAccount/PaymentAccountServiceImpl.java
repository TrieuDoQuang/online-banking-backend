package com.example.onlinebankingapp.services.PaymentAccount;

import com.example.onlinebankingapp.dtos.PaymentAccountDTO;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.enums.AccountStatus;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.repositories.CustomerRepository;
import com.example.onlinebankingapp.repositories.PaymentAccountRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentAccountServiceImpl implements PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;

    private final CustomerRepository customerRepository;
    @Override
    public PaymentAccountEntity insertPaymentAccount(PaymentAccountDTO paymentAccountDTO) throws DataNotFoundException {

        String accountNumber = paymentAccountDTO.getAccountNumber();
        if(paymentAccountRepository.existsByAccountNumber(accountNumber)){
            throw new DataIntegrityViolationException("The Account Number already exists");
        }

        PaymentAccountEntity newPaymentAccount = PaymentAccountEntity.builder()
                .accountNumber(accountNumber)
                .currentBalance(paymentAccountDTO.getCurrentBalance())
                .build();

        long customerId = paymentAccountDTO.getCustomerId();

        boolean isExistingDefaultAccount = paymentAccountRepository.checkExistingByStatus(customerId, AccountStatus.DEFAULT);

        if(!isExistingDefaultAccount) {
            newPaymentAccount.setAccountStatus(AccountStatus.DEFAULT);
        }

        CustomerEntity customer = customerRepository.getReferenceById(customerId);

        newPaymentAccount.setCustomer(customer);

        return paymentAccountRepository.save(newPaymentAccount);

    }

    public void setDefaultPaymentAccount(long customerId, String accountNumber) throws DataNotFoundException {

        PaymentAccountEntity existingDefaultPaymentAccount = getDefaultPaymentAccount(customerId);

        existingDefaultPaymentAccount.setAccountStatus(AccountStatus.ACTIVE);

        PaymentAccountEntity paymentAccountToSetDefault = paymentAccountRepository.getPaymentAccountByAccountNumber(accountNumber);

        paymentAccountToSetDefault.setAccountStatus(AccountStatus.DEFAULT);

        paymentAccountRepository.save(existingDefaultPaymentAccount);

        paymentAccountRepository.save(paymentAccountToSetDefault);
    }

    @Override
    public List<PaymentAccountEntity> getAllPaymentAccounts() throws DataNotFoundException {
        return paymentAccountRepository.findAll();
    }

    @Override
    public PaymentAccountEntity getPaymentAccountById(long id) throws DataNotFoundException {

        Optional<PaymentAccountEntity> optionalPaymentAccount = paymentAccountRepository.findById(id);
        if(optionalPaymentAccount.isPresent()) {
            return optionalPaymentAccount.get();
        }
        throw new DataNotFoundException("Cannot find Payment Account with Id: " + id);
    }

    @Override
    public PaymentAccountEntity getDefaultPaymentAccount(long customerId) throws DataNotFoundException {
        Optional<PaymentAccountEntity> optionalPaymentAccount = paymentAccountRepository.getPaymentAccountByStatus(customerId, AccountStatus.DEFAULT);
        if(optionalPaymentAccount.isPresent()) {
            return optionalPaymentAccount.get();
        }
        throw new DataNotFoundException("Cannot find Default Payment Account");
    }
}
