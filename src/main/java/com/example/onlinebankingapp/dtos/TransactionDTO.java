package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
//in charge: dat
@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO //dto for transaction
{


    private Double amount; // Field for transaction amount

    @JsonProperty("transaction_date_time")
    private Timestamp transactionDateTime; // Field for transaction date time with JSON property name annotation

    @JsonProperty("transaction_remark")
    private String transactionRemark; // Field for transaction remark with JSON property name annotation

    @JsonProperty("sender_account_number")
    private String senderAccountNumber; // Field for sender account number with JSON property name annotation

    @JsonProperty("sender_email")
    private String senderEmail; // Field for sender email with JSON property name annotation

    @JsonProperty("receiver_account_number")
    private String receiverAccountNumber;  // Field for receiver account number with JSON property name annotation

    @JsonProperty("receiver_account_name")
    private String receiverAccountName; // Field for receiver account name with JSON property name annotation
}
