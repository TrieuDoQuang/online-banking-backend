package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//in charge: khai
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRewardDTO { //data transfer object class for account reward
    // JSON property for reward ID
    @JsonProperty("reward_id")
    private Long rewardId;
    // JSON property for payment account ID
    @JsonProperty("payment_account_id")
    private Long paymentAccountId;
    // JSON property for indicating validity
    @JsonProperty("is_valid")
    private Boolean isValid;
}
