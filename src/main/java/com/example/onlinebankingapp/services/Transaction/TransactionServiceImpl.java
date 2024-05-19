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

        long senderId = transactionDTO.getSenderId();
        long receiverId = transactionDTO.getReceiverId();

        if(senderId == receiverId) {
            throw new BadRequestException("Cannot make transaction to your current account");
        }

        Optional<PaymentAccountEntity> optionalSender = paymentAccountRepository.findById(senderId);
        Optional<PaymentAccountEntity> optionalReceiver = paymentAccountRepository.findById(receiverId);

        if(optionalReceiver.isPresent() && optionalSender.isPresent()) {
            PaymentAccountEntity sender = optionalSender.get();
            PaymentAccountEntity receiver = optionalReceiver.get();

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
