package com.example.onlinebankingapp.services.PaymentAccount;

import com.example.onlinebankingapp.dtos.PaymentAccountDTO;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.enums.AccountStatus;
import com.example.onlinebankingapp.entities.enums.AccountType;
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

    private final PaymentAccountRepository paymentAccountRepository;

    private final CustomerRepository customerRepository;
    @Override
    public PaymentAccountEntity insertPaymentAccount(PaymentAccountDTO paymentAccountDTO) throws DataNotFoundException {

        String accountNumber = paymentAccountDTO.getAccountNumber();
        if(paymentAccountRepository.existsByAccountNumber(accountNumber)){
            throw new DataIntegrityViolationException("Số Tài Khoản đã tồn tại");
        }

        PaymentAccountEntity newPaymentAccount =PaymentAccountEntity.builder()
                .accountNumber(accountNumber)
                .accountStatus(AccountStatus.valueOf(paymentAccountDTO.getAccountStatus()))
                .accountType(AccountType.valueOf(paymentAccountDTO.getAccountType()))
                .currentBalance(paymentAccountDTO.getCurrentBalance())
                .build();

        CustomerEntity customer = customerRepository.getReferenceById(paymentAccountDTO.getCustomerId());

        newPaymentAccount.setCustomer(customer);

        return paymentAccountRepository.save(newPaymentAccount);

    }

    @Override
    public List<PaymentAccountEntity> getAllPaymentAccounts() throws Exception {
        return paymentAccountRepository.findAll();
    }

    @Override
    public PaymentAccountEntity getPaymentAccountById(long id) throws Exception {

        Optional<PaymentAccountEntity> optionalPaymentAccount = paymentAccountRepository.findById(id);
        if(optionalPaymentAccount.isPresent()) {
            return optionalPaymentAccount.get();
        }
        throw new DataNotFoundException("Không tìm thấy tài khoản có ID: " + id);
    }
}
