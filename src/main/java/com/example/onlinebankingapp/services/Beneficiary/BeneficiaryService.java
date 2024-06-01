package com.example.onlinebankingapp.services.Beneficiary;

import com.example.onlinebankingapp.dtos.BeneficiaryDTO;
import com.example.onlinebankingapp.entities.BeneficiaryEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

//interface defining abstract functions for Beneficiary Service
//in charge: dat
public interface BeneficiaryService {


    // Insert a new beneficiary into the system
    BeneficiaryEntity insertBeneficiary(BeneficiaryDTO beneficiaryDTO) throws Exception;

    // Retrieve a beneficiary by their ID
    BeneficiaryEntity getBeneficiaryById(long id) throws Exception;

    // Retrieve all beneficiaries associated with a customer by their customer ID
    List<BeneficiaryEntity> getBeneficiariesByCustomerId(long customerId) throws Exception;

    // Delete a beneficiary by their ID
    void deleteBeneficiary(long id) throws ExecutionException;
}
