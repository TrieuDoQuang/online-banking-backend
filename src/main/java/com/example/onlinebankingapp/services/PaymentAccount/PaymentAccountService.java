package com.example.onlinebankingapp.services.PaymentAccount;

import com.example.onlinebankingapp.dtos.AmountOperationDTO;
import com.example.onlinebankingapp.dtos.PaymentAccountDTO;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;

import java.util.List;

public interface PaymentAccountService {


    PaymentAccountEntity insertPaymentAccount(PaymentAccountDTO paymentAccountDTO) throws DataNotFoundException;

    List<PaymentAccountEntity> getAllPaymentAccounts() throws DataNotFoundException;

    void setDefaultPaymentAccount(long customerId, String accountNumber) throws DataNotFoundException;

    PaymentAccountEntity getPaymentAccountById(long id) throws DataNotFoundException;


    PaymentAccountEntity getPaymentAccountByAccountNumber(String accountNumber) throws DataNotFoundException;

    PaymentAccountEntity getDefaultPaymentAccount(long customerId) throws DataNotFoundException;

    List<PaymentAccountEntity> getPaymentAccountsByCustomerId(long customerId) throws  DataNotFoundException;

    void topUpPaymentAccount(long paymentAccountId, AmountOperationDTO amountDTO) throws  DataNotFoundException;

    void withdrawPaymentAccount (long paymentAccountId, AmountOperationDTO amountDTO) throws  DataNotFoundException;

}
