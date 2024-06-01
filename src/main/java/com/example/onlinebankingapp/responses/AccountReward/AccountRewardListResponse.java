package com.example.onlinebankingapp.responses.AccountReward;

import com.example.onlinebankingapp.responses.Reward.RewardResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class AccountRewardListResponse {
    private List<AccountRewardResponse> accountRewards;
    private int totalPages;
}
