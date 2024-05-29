package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.entities.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAccountDTO extends AbstractAccountDTO {

    @JsonProperty("current_balance")
    private Double currentBalance;

    @JsonProperty("reward_point")
    private Integer rewardPoint;

    @JsonProperty("customer_id")
    private Long customerId;

}
