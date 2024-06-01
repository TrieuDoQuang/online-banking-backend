package com.example.onlinebankingapp.responses.Reward;

import com.example.onlinebankingapp.entities.RewardEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//in charge: khai
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardResponse //custom responsee for reward data
{
    //reward fields
    @JsonProperty("id")
    private Long id;

    @JsonProperty("cost_point")
    private Integer costPoint;

    @JsonProperty("reward_name")
    private String rewardName;

    @JsonProperty("reward_type")
    private String rewardType;

    @JsonProperty("image_link")
    private String imageLink;

    // Static method to convert RewardEntity to RewardResponse
    public static RewardResponse fromReward(RewardEntity reward){
        return RewardResponse.builder()
                .id(reward.getId())
                .rewardName(reward.getRewardName())
                .costPoint(reward.getCostPoint())
                .rewardType(reward.getRewardType().name())
                .imageLink(createImageLink(reward.getId().toString()))
                .build();
    }

    // Method to create image link that can be accessed via server api
    private static String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/rewards/image/" + filename)
                .toUriString();
    }
}
