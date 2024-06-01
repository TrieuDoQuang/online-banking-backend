package com.example.onlinebankingapp.responses.Transaction;

import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.entities.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
//in charge: dat
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse //custom response for transaction data
{

    //transaction fields in response
    private Long id;

    private Double amount;

    @JsonProperty("transaction_date_time")
    private Timestamp transactionDateTime;

    @JsonProperty("transaction_remark")
    private String transactionRemark;

    @JsonProperty("sender_account_number")
    private String senderAccountNumber;

    @JsonProperty("receiver_account_number")
    private String receiverAccountNumber;

    // Static method to convert TransactionEntity to TransactionResponse
    public static TransactionResponse fromTransaction(TransactionEntity transaction){
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .senderAccountNumber(transaction.getSender().getAccountNumber())
                .receiverAccountNumber(transaction.getReceiver().getAccountNumber())
                .transactionDateTime(transaction.getTransactionDateTime())
                .transactionRemark(transaction.getTransactionRemark())
                .build();
    }
}
