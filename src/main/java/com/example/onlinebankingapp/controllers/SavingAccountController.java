package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.responses.SavingAccount.SavingAccountListResponse;
import com.example.onlinebankingapp.responses.SavingAccount.SavingAccountResponse;
import com.example.onlinebankingapp.services.SavingAccount.SavingAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savingAccounts")
@RequiredArgsConstructor
public class SavingAccountController {
    private final SavingAccountService savingAccountService;

    @PostMapping("")
    public ResponseEntity<?> insertSavingAccount(@Valid @RequestBody SavingAccountDTO savingAccountDTO){
        try{
            SavingAccountEntity savingAccountResponse = savingAccountService.insertSavingAccount(savingAccountDTO);
            return ResponseEntity.ok(ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Create saving account successfully!")
                            .result(SavingAccountResponse.fromSavingAccount(savingAccountResponse))
                            .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSavingAccounts(){
        try{
            List<SavingAccountEntity> savingAccountEntityList = savingAccountService.getAllSavingAccounts();

            List<SavingAccountResponse> savingAccountResponses = savingAccountEntityList.stream()
                    .map(SavingAccountResponse::fromSavingAccount)
                    .toList();

            SavingAccountListResponse savingAccountListResponse = SavingAccountListResponse
                    .builder()
                    .savingAccounts(savingAccountResponses)
                    .build();

            return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("Get Saving account list successfully!")
                            .status(HttpStatus.OK)
                            .result(savingAccountListResponse)
                            .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
