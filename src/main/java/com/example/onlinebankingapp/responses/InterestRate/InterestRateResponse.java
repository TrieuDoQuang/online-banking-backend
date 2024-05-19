package com.example.onlinebankingapp.responses.InterestRate;

import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestRateResponse {
    private long id;

    @JsonProperty("interest_rate")
    private Double interestRate;

    @JsonProperty("teerm")
    private Integer term;

    public static InterestRateResponse fromInterestRate(InterestRateEntity interestRate){
        return InterestRateResponse.builder()
                .id(interestRate.getId())
                .interestRate(interestRate.getInterestRate())
                .term(interestRate.getTerm())
                .build();
    }
}
