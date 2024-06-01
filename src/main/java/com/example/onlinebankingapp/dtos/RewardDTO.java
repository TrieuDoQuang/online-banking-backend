package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.enums.RewardType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
//in charge: khai
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardDTO //dto for reward
{
    @JsonProperty("cost_point")
    private Integer costPoint; // Field for cost point with JSON property name annotation

    @JsonProperty("reward_name")
    private String rewardName; // Field for reward name with JSON property name annotation

    @JsonProperty("reward_type")
    private String rewardType; // Field for reward type with JSON property name annotation

//    @JsonProperty("image")
//    private byte[] image;
}
