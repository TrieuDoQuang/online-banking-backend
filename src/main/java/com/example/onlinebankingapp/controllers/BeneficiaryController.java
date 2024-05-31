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

    @PostMapping("")
    public ResponseEntity<?> insertBeneficiary(
            @Valid @RequestBody BeneficiaryDTO beneficiaryDTO
    ) {
        try {
            BeneficiaryEntity beneficiary = beneficiaryService.insertBeneficiary(beneficiaryDTO);
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

    @GetMapping("{id}")
    public ResponseEntity<?> getBeneficiaryById(@Valid @PathVariable("id") Long id){
        try{
            BeneficiaryEntity beneficiary = beneficiaryService.getBeneficiaryById(id);

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get Beneficiary List Successfully!")
                    .result(BeneficiaryResponse.fromBeneficiary(beneficiary))
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getByCustomerId/{id}")
    public ResponseEntity<?> getBeneficiaryByCustomerId(@Valid @PathVariable("id") Long customerId){
        try{
            List<BeneficiaryEntity> beneficiaries = beneficiaryService.getBeneficiariesByCustomerId(customerId);

            List<BeneficiaryResponse> beneficiariesResponse = beneficiaries.stream()
                    .map(BeneficiaryResponse::fromBeneficiary)
                    .toList();

            BeneficiaryListResponse beneficiaryListResponse = BeneficiaryListResponse.builder()
                    .beneficiaries(beneficiariesResponse)
                    .build();

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Get Beneficiary List Successfully!")
                    .result(beneficiaryListResponse)
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBeneficiary(@Valid @PathVariable("id") Long id){

        try{
            beneficiaryService.deleteBeneficiary(id);

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Delete Beneficiary Successfully!")
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
