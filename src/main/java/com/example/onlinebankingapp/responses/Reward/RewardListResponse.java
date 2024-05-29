package com.example.onlinebankingapp.responses.Reward;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class RewardListResponse {
    private List<RewardResponse> rewards;
    private int totalPages;
}
