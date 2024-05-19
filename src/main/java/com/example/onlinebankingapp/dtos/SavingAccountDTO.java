package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountDTO extends AbstractAccountDTO {
    @JsonProperty("min_balance")
    private int minBalance;

    @JsonProperty("saving_current_amount")
    private Double savingCurrentAmount;

    @JsonProperty("saving_initial_amount")
    private Double savingInitialAmount;

    @JsonProperty("payment_account_id")
    private Long paymentAccountId;

    @JsonProperty("interest_rate_id")
    private Long interestRateId;
}
