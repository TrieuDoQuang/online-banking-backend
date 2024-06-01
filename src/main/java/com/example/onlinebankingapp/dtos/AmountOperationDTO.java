package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
//in charge: trieu
@Data
@Getter
@Setter
@NoArgsConstructor

public class AmountOperationDTO //data transfer object for amount operation
{
    // JSON property for amount
    @JsonProperty("amount")
    private double amount;
}
