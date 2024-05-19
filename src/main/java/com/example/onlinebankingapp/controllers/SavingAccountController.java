package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.responses.SavingAccount.SavingAccountResponse;
import com.example.onlinebankingapp.services.SavingAccount.SavingAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
