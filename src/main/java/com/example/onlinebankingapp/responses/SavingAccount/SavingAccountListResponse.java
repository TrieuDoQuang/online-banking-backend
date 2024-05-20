package com.example.onlinebankingapp.responses.SavingAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class SavingAccountListResponse {
    private List<SavingAccountResponse> savingAccounts;
    private int totalPages;
}
