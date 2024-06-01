package com.example.onlinebankingapp.responses.PaymentAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//in charge: dat
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class PaymentAccountListResponse //custom response for list of payment accounts
{

    private List<PaymentAccountResponse> paymentAccounts;
    private int totalPages;
}
