package com.example.onlinebankingapp.responses.Beneficiary;

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
public class BeneficiaryListResponse //custom response for list of benificiaries
{

    private List<BeneficiaryResponse> beneficiaries;

    private int totalPages;
}
