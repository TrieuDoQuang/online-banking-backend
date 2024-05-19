package com.example.onlinebankingapp.responses.PaymentAccount;

import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentAccountResponse {

    private Long id;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_status")
    private String accountStatus;

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("date_closed")
    private Date dateClosed;

    @JsonProperty("date_opened")
    private Date dateOpened;

    @JsonProperty("current_balance")
    private Double currentBalance;

    @JsonProperty("reward_point")
    private Integer rewardPoint;

    @JsonProperty("customer_id")
    private Long customerId;

    public static PaymentAccountResponse fromPaymentAccount(PaymentAccountEntity paymentAccount) {
        return PaymentAccountResponse
                .builder()
                .id(paymentAccount.getId())
                .accountNumber(paymentAccount.getAccountNumber())
                .accountType(paymentAccount.getAccountType().name())
                .accountStatus(paymentAccount.getAccountStatus().name())
                .currentBalance(paymentAccount.getCurrentBalance())
                .rewardPoint(paymentAccount.getRewardPoint())
                .customerId(paymentAccount.getCustomer().getId())
                .dateOpened(paymentAccount.getDateOpened())
                .dateClosed(paymentAccount.getDateClosed())
                .build();
    }
}
