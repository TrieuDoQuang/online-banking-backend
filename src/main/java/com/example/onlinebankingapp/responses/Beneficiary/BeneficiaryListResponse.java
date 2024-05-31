package com.example.onlinebankingapp.responses.Beneficiary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class BeneficiaryListResponse {

    private List<BeneficiaryResponse> beneficiaries;

    private int totalPages;
}
