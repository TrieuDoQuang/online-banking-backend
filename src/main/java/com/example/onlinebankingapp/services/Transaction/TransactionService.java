package com.example.onlinebankingapp.services.Transaction;

import com.example.onlinebankingapp.dtos.TransactionDTO;
import com.example.onlinebankingapp.entities.TransactionEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

// Interface defining abstract functions for Transaction Service
// It is responsible for defining operations related to transactions
// In charge: dat
public interface TransactionService {

    // Make a transaction using the provided transaction DTO
    TransactionEntity makeTransaction(TransactionDTO transactionDTO) throws DataNotFoundException, BadRequestException;

    // Get all transactions associated with a particular customer ID
    List<TransactionEntity> getTransactionsByCustomerId(long customerId) throws DataNotFoundException;
}
