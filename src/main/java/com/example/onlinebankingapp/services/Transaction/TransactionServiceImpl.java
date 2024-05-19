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

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final PaymentAccountRepository paymentAccountRepository;

    @Override
    public TransactionEntity makeTransaction(TransactionDTO transactionDTO) throws DataNotFoundException, BadRequestException {

        Double transactionAmount = transactionDTO.getAmount();

        TransactionEntity newTransaction = TransactionEntity.builder()
                .amount(transactionAmount)
                .transactionRemark(transactionDTO.getTransactionRemark())
                .build();

        String senderAccountNumber = transactionDTO.getSenderAccountNumber();
        String receiverAccountNumber = transactionDTO.getReceiverAccountNumber();

        if(Objects.equals(senderAccountNumber, receiverAccountNumber)) {
            throw new BadRequestException("Cannot make transaction to your current account");
        }

        PaymentAccountEntity sender = paymentAccountRepository.getPaymentAccountByAccountNumber(senderAccountNumber);
        PaymentAccountEntity receiver = paymentAccountRepository.getPaymentAccountByAccountNumber(receiverAccountNumber);

        if(sender != null && receiver != null) {

            newTransaction.setSender(sender);
            newTransaction.setReceiver(receiver);

            Double senderCurrentBalance = sender.getCurrentBalance();
            sender.setCurrentBalance(senderCurrentBalance - transactionAmount);

            Double receiverCurrentBalance = receiver.getCurrentBalance();
            receiver.setCurrentBalance(receiverCurrentBalance + transactionAmount);
        }
        else {
            throw new DataNotFoundException("Sender or receiver does not exist");
        }

        return transactionRepository.save(newTransaction);
    }
}
