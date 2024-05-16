package com.example.onlinebankingapp.responses.PaymentAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class PaymentAccountListResponse {

    private List<PaymentAccountResponse> paymentAccounts;
    private int totalPages;
}
