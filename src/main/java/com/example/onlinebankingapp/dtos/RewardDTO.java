package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.enums.RewardType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardDTO {
    @JsonProperty("cost_point")
    private Integer costPoint;

    @JsonProperty("reward_name")
    private String rewardName;

    @JsonProperty("reward_type")
    private String rewardType;
}
