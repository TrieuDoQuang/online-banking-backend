package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("customer_id")
    private long customerId;

    @JsonProperty("payment_account_id")
    private long paymentAccountId;

    @JsonProperty("account_number")
    private String accountNumber;

}
