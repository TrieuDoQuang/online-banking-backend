package com.example.onlinebankingapp.responses.Reward;

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
public class RewardListResponse //custom response for list of rewards
{
    private List<RewardResponse> rewards;
    private int totalPages;
}
