package com.example.onlinebankingapp.responses.AccountReward;

import com.example.onlinebankingapp.entities.AccountRewardEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.RewardEntity;
import com.example.onlinebankingapp.responses.Reward.RewardResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
// in charge: khai
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRewardResponse //custom response for account reward data
{
    //account reward data fields
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

    // Static method to create an AccountRewardResponse object from an AccountRewardEntity object
    public static AccountRewardResponse fromAccountReward(AccountRewardEntity accountReward){
        RewardEntity reward = accountReward.getAccountRewardKey().getReward();
        PaymentAccountEntity paymentAccount = accountReward.getAccountRewardKey().getPaymentAccount();

        return AccountRewardResponse.builder()
                .rewardId(reward.getId())
                .paymentAccountId(paymentAccount.getId())
                .rewardName(reward.getRewardName())
                .costPoint(reward.getCostPoint())
                .rewardType(String.valueOf(reward.getRewardType()))
                .imageLink(createImageLink(reward.getId().toString()))
                .isValid(accountReward.isValid())
                .build();
    }

    // Private method to create an image link that can be accessed via server api
    private static String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/api/rewards/image/" + filename)
                .toUriString();
    }
}
