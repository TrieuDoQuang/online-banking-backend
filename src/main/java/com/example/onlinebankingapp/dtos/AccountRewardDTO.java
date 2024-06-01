package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRewardDTO {
    @JsonProperty("reward_id")
    private Long rewardId;
    @JsonProperty("payment_account_id")
    private Long paymentAccountId;
    @JsonProperty("is_valid")
    private Boolean isValid;
}
