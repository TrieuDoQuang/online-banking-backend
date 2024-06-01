package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.entities.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
//in charge: dat
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAccountDTO extends AbstractAccountDTO //dto for payment account, this class extends the AbstractAccountDTO
{

    @JsonProperty("current_balance")
    private Double currentBalance; //account's current balance

    @JsonProperty("reward_point")
    private Integer rewardPoint; //account's reward points

    @JsonProperty("customer_id")
    private Long customerId; //customer who owns the account

}
