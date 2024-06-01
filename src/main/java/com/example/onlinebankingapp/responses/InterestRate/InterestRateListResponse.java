package com.example.onlinebankingapp.responses.InterestRate;

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
public class InterestRateListResponse //custom response for list of interest rates
{
    private List<InterestRateResponse> interestRateResponses;
    private int totalPages;
}
