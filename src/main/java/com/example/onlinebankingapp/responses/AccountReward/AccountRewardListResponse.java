package com.example.onlinebankingapp.responses.AccountReward;

import com.example.onlinebankingapp.responses.Reward.RewardResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//in charge: khai
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class AccountRewardListResponse //custom response for list of account rewards
{
    private List<AccountRewardResponse> accountRewards;
    private int totalPages;
}
