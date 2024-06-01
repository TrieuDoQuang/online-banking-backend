package com.example.onlinebankingapp.responses.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
// in charge: dat
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class TransactionListResponse //custom response for list of transactions
{

    private List<TransactionResponse> transactions;

    private int totalPages;
}
