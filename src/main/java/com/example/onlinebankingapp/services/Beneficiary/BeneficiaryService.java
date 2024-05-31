package com.example.onlinebankingapp.services.Beneficiary;

import com.example.onlinebankingapp.dtos.BeneficiaryDTO;
import com.example.onlinebankingapp.entities.BeneficiaryEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BeneficiaryService {


    BeneficiaryEntity insertBeneficiary(BeneficiaryDTO beneficiaryDTO) throws Exception;

    BeneficiaryEntity getBeneficiaryById(long id) throws Exception;

    List<BeneficiaryEntity> getBeneficiariesByCustomerId(long customerId) throws Exception;

    void deleteBeneficiary(long id) throws ExecutionException;
}
