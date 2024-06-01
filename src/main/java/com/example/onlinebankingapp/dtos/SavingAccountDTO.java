package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
// in charge: khai
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountDTO extends AbstractAccountDTO //dto for saving account, this class extends AbstractAccountDTO
{
    @JsonProperty("saving_current_amount")
    private Double savingCurrentAmount; // Field for saving current amount with JSON property name annotation

    @JsonProperty("saving_initial_amount")
    private Double savingInitialAmount; // Field for saving initial amount with JSON property name annotation

    @JsonProperty("payment_account_id")
    private Long paymentAccountId; // Field for saving initial amount with JSON property name annotation

    @JsonProperty("interest_rate_id")
    private Long interestRateId; // Field for interest rate ID with JSON property name annotation
}
