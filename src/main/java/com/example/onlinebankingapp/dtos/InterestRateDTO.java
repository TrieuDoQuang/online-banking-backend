package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class InterestRateDTO {
    @JsonProperty("interest_rate")
    private Double interestRate;
    @JsonProperty("term")
    private Integer term;
}
