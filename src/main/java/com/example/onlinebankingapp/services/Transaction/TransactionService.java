package com.example.onlinebankingapp.services.Transaction;

import com.example.onlinebankingapp.dtos.TransactionDTO;
import com.example.onlinebankingapp.entities.TransactionEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import org.apache.coyote.BadRequestException;

public interface TransactionService {

    TransactionEntity makeTransaction(TransactionDTO transactionDTO) throws DataNotFoundException, BadRequestException;


}
