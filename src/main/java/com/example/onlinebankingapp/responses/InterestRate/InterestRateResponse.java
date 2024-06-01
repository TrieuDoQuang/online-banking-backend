package com.example.onlinebankingapp.responses.InterestRate;

import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
// in charge; khai
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestRateResponse //custom response for interest rate data
{
    //interest rate fields
    private long id;

    @JsonProperty("interest_rate")
    private Double interestRate;

    @JsonProperty("term")
    private Integer term;

    @JsonProperty("min_balance")
    private Double minBalance;

    // Static method to convert InterestRateEntity to InterestRateResponse
    public static InterestRateResponse fromInterestRate(InterestRateEntity interestRate){
        return InterestRateResponse.builder()
                .id(interestRate.getId())
                .interestRate(interestRate.getInterestRate())
                .term(interestRate.getTerm())
                .minBalance(interestRate.getMinBalance())
                .build();
    }
}
