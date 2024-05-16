package com.example.onlinebankingapp.services.PaymentAccount;

import com.example.onlinebankingapp.dtos.PaymentAccountDTO;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;

import java.util.List;

public interface PaymentAccountService {


    PaymentAccountEntity insertPaymentAccount(PaymentAccountDTO paymentAccountDTO) throws DataNotFoundException;

    List<PaymentAccountEntity> getAllPaymentAccounts() throws Exception;

    PaymentAccountEntity getPaymentAccountById(long id) throws Exception;




}
