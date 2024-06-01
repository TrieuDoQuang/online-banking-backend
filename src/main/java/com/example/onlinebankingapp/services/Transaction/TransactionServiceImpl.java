package com.example.onlinebankingapp.services.Transaction;

import com.example.onlinebankingapp.dtos.TransactionDTO;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.TransactionEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.repositories.PaymentAccountRepository;
import com.example.onlinebankingapp.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final PaymentAccountRepository paymentAccountRepository;

    //make transaction between accounts
    //in charge: dat
    @Override
    public TransactionEntity makeTransaction(TransactionDTO transactionDTO) throws DataNotFoundException, BadRequestException {

        Double transactionAmount = transactionDTO.getAmount();

        // Create a new transaction entity
        TransactionEntity newTransaction = TransactionEntity.builder()
                .amount(transactionAmount)
                .transactionRemark(transactionDTO.getTransactionRemark())
                .build();

        String senderAccountNumber = transactionDTO.getSenderAccountNumber();
        String receiverAccountNumber = transactionDTO.getReceiverAccountNumber();

        // Check if sender and receiver account numbers are the same
        if(Objects.equals(senderAccountNumber, receiverAccountNumber)) {
            throw new BadRequestException("Cannot make transaction to your current account");
        }

        // Retrieve sender and receiver payment account entities
        PaymentAccountEntity sender = paymentAccountRepository.getPaymentAccountByAccountNumber(senderAccountNumber);
        PaymentAccountEntity receiver = paymentAccountRepository.getPaymentAccountByAccountNumber(receiverAccountNumber);

        if(sender != null && receiver != null) {

            // Set sender and receiver for the new transaction
            newTransaction.setSender(sender);
            newTransaction.setReceiver(receiver);

            // Update sender and receiver balances
            Double senderCurrentBalance = sender.getCurrentBalance();
            sender.setCurrentBalance(senderCurrentBalance - transactionAmount);

            Double receiverCurrentBalance = receiver.getCurrentBalance();
            receiver.setCurrentBalance(receiverCurrentBalance + transactionAmount);
        }
        else {
            throw new DataNotFoundException("Sender or receiver does not exist");
        }

        // Save the new transaction
        return transactionRepository.save(newTransaction);
    }

    //get all transactions of a customer
    //in charge: dat
    @Override
    public List<TransactionEntity> getTransactionsByCustomerId(long customerId) throws DataNotFoundException {
        // Retrieve transactions by customer ID
        List<TransactionEntity> transactions = transactionRepository.findTransactionsByCustomerId(customerId);

        if(transactions.isEmpty()) {
            throw new DataNotFoundException("Cannot find Transactions History");
        }

        return transactions;
    }
}
