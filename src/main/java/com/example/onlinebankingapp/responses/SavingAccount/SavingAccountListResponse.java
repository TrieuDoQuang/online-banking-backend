package com.example.onlinebankingapp.responses.SavingAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
// in charge: khai
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class SavingAccountListResponse //custom response for list of saving accounts
{
    private List<SavingAccountResponse> savingAccounts;
    private int totalPages;
}
