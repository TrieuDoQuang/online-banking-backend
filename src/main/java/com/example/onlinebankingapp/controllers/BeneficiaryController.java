package com.example.onlinebankingapp.controllers;


import com.example.onlinebankingapp.dtos.BeneficiaryDTO;
import com.example.onlinebankingapp.dtos.InterestRateDTO;
import com.example.onlinebankingapp.entities.BeneficiaryEntity;
import com.example.onlinebankingapp.entities.InterestRateEntity;
import com.example.onlinebankingapp.responses.Beneficiary.BeneficiaryListResponse;
import com.example.onlinebankingapp.responses.Beneficiary.BeneficiaryResponse;
import com.example.onlinebankingapp.responses.InterestRate.InterestRateListResponse;
import com.example.onlinebankingapp.responses.InterestRate.InterestRateResponse;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.responses.Reward.RewardListResponse;
import com.example.onlinebankingapp.responses.Reward.RewardResponse;
import com.example.onlinebankingapp.services.Beneficiary.BeneficiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    //end point for inserting a beneficiary
    //in charge: Dat
    @PostMapping("")
    public ResponseEntity<?> insertBeneficiary(
            @Valid @RequestBody BeneficiaryDTO beneficiaryDTO
    ) {
        try {
            // Insert the beneficiary using the beneficiaryService
            BeneficiaryEntity beneficiary = beneficiaryService.insertBeneficiary(beneficiaryDTO);

            // Return a successful response with the inserted beneficiary details
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Add Beneficiary Successfully")
                            .result(BeneficiaryResponse.fromBeneficiary(beneficiary))
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for getting a beneficiary by its id
    //in charge: Dat
    @GetMapping("{id}")
    public ResponseEntity<?> getBeneficiaryById(@Valid @PathVariable("id") Long id){
        try{
            // Retrieve the beneficiary by ID using the beneficiaryService
            BeneficiaryEntity beneficiary = beneficiaryService.getBeneficiaryById(id);

            // Return a successful response with the beneficiary details
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get Beneficiary List Successfully!")
                    .result(BeneficiaryResponse.fromBeneficiary(beneficiary))
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for getting all beneficiaries of a customer
    //in charge: Dat
    @GetMapping("/getByCustomerId/{id}")
    public ResponseEntity<?> getBeneficiaryByCustomerId(@Valid @PathVariable("id") Long customerId){
        try{
            // Retrieve beneficiaries by customer ID using the beneficiaryService
            List<BeneficiaryEntity> beneficiaries = beneficiaryService.getBeneficiariesByCustomerId(customerId);

            //build response
            List<BeneficiaryResponse> beneficiariesResponse = beneficiaries.stream()
                    .map(BeneficiaryResponse::fromBeneficiary)
                    .toList();

            BeneficiaryListResponse beneficiaryListResponse = BeneficiaryListResponse.builder()
                    .beneficiaries(beneficiariesResponse)
                    .build();

            // Return a successful response with the list of beneficiaries
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get Beneficiary List Successfully!")
                    .result(beneficiaryListResponse)
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for deleting a beneficiary
    //in charge: Dat
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBeneficiary(@Valid @PathVariable("id") Long id){

        try{
            //delete the requested beneficiary
            beneficiaryService.deleteBeneficiary(id);

            //return result in response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Delete Beneficiary Successfully!")
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
