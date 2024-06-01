package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

//in charge: dat
@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryDTO //data transfer object for beneficiary
{

    @JsonProperty("name")
    private String name; // JSON property for beneficiary name

    @JsonProperty("customer_id")
    private long customerId; // JSON property for customer ID

    @JsonProperty("payment_account_id")
    private long paymentAccountId; // JSON property for payment account ID

    @JsonProperty("account_number")
    private String accountNumber; // JSON property for account number

}
