package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {


    private Double amount;

    @JsonProperty("transaction_date_time")
    private Timestamp transactionDateTime;

    @JsonProperty("transaction_remark")
    private String transactionRemark;

    @JsonProperty("sender_account_number")
    private String senderAccountNumber;

    @JsonProperty("receiver_account_number")
    private String receiverAccountNumber;

    @JsonProperty("receiver_account_name")
    private String receiverAccountName;
}
