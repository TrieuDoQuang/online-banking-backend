package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor

public class AmountOperationDTO {
    @JsonProperty("amount")
    private double amount;
}
