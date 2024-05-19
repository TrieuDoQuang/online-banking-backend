package com.example.onlinebankingapp.responses.InterestRate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class InterestRateListResponse {
    private List<InterestRateResponse> interestRateResponses;
    private int totalPages;
}
