package com.example.onlinebankingapp.responses.AccountReward;

import com.example.onlinebankingapp.entities.AccountRewardEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
import com.example.onlinebankingapp.responses.Reward.RewardResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRewardResponse {
    @JsonProperty("reward_id")
    private Long rewardId;

    @JsonProperty("payment_account_id")
    private Long paymentAccountId;

    @JsonProperty("is_valid")
    private Boolean isValid;

    @JsonProperty("cost_point")
    private Integer costPoint;

    @JsonProperty("reward_name")
    private String rewardName;

    @JsonProperty("reward_type")
    private String rewardType;

    @JsonProperty("image_link")
    private String imageLink;

    public static AccountRewardResponse fromAccountReward(AccountRewardEntity accountReward){
        RewardEntity reward = accountReward.getAccountRewardKey().getReward();
        PaymentAccountEntity paymentAccount = accountReward.getAccountRewardKey().getPaymentAccount();

        return AccountRewardResponse.builder()
                .rewardId(reward.getId())
                .paymentAccountId(paymentAccount.getId())
                .rewardName(reward.getRewardName())
                .costPoint(reward.getCostPoint())
                .rewardType(String.valueOf(reward.getRewardType()))
                .imageLink(createImageLink(reward.getRewardName()))
                .isValid(accountReward.isValid())
                .build();
    }

    private static String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/rewards/image/" + filename)
                .toUriString();
    }
}
