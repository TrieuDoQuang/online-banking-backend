package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.components.SecurityUtils;
import com.example.onlinebankingapp.dtos.SavingAccountDTO;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.SavingAccountEntity;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.responses.SavingAccount.SavingAccountListResponse;
import com.example.onlinebankingapp.responses.SavingAccount.SavingAccountResponse;
import com.example.onlinebankingapp.services.SavingAccount.SavingAccountService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savingAccounts")
@RequiredArgsConstructor
public class SavingAccountController {
    private final SavingAccountService savingAccountService;

    // Endpoint for creating a new saving account
    // In charge person: Khai
    @PostMapping("")
    public ResponseEntity<?> insertSavingAccount(@Valid @RequestBody SavingAccountDTO savingAccountDTO){
        try{
            //call service layer to insert a saving account
            SavingAccountEntity savingAccountResponse = savingAccountService.insertSavingAccount(savingAccountDTO);

            //return response
            return ResponseEntity.ok(ResponseObject.builder()
                            .status(HttpStatus.OK)
                            .message("Create saving account successfully!")
                            .result(SavingAccountResponse.fromSavingAccount(savingAccountResponse))
                            .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for retrieving a list of all saving accounts
    // In charge person: Khai
    @GetMapping("")
    public ResponseEntity<?> getAllSavingAccounts(){
        try{
            //call service layer to get all saving accounts
            List<SavingAccountEntity> savingAccountEntityList = savingAccountService.getAllSavingAccounts();

            //creating response
            List<SavingAccountResponse> savingAccountResponses = savingAccountEntityList.stream()
                    .map(SavingAccountResponse::fromSavingAccount)
                    .toList();

            SavingAccountListResponse savingAccountListResponse = SavingAccountListResponse
                    .builder()
                    .savingAccounts(savingAccountResponses)
                    .build();

            //return response
            return ResponseEntity.ok().body(ResponseObject.builder()
                            .message("Get Saving account list successfully!")
                            .status(HttpStatus.OK)
                            .result(savingAccountListResponse)
                            .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for retrieving a specific saving account by ID
    // In charge person: Khai
    @GetMapping("/{id}")
    public ResponseEntity<?> getSavingAccountById(@Valid @PathVariable("id") Long id){
        try{
            //call service layer to get a saving account
            SavingAccountEntity savingAccount = savingAccountService.getSavingAccountById(id);

            //return response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Get saving account successfully!")
                    .status(HttpStatus.OK)
                    .result(SavingAccountResponse.fromSavingAccount(savingAccount))
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for withdrawing money from a saving account
    // In charge person: Khai
    @PutMapping("/withdraw/{id}")
    public ResponseEntity<?> withdrawSavingAccount(@Valid @PathVariable("id") Long id){
        try{
            //call service layer to withdraw a saving account
            SavingAccountEntity savingAccount = savingAccountService.withdrawSavingAccount(id);

            //return response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Withdraw saving account successfully!")
                    .status(HttpStatus.OK)
                    .result(SavingAccountResponse.fromSavingAccount(savingAccount))
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for retrieving all saving accounts of a user
    // In charge person: Khai
    @GetMapping("/userSavingAccount/{id}")
    public ResponseEntity<?> getUserSavingAccounts(@Valid @PathVariable("id") Long userId){
        try{
            //call service layer to get all saving accounts of a user
            List<SavingAccountEntity> savingAccountEntityList = savingAccountService.getSavingAccountsOfUser(userId);

            //creating response
            List<SavingAccountResponse> savingAccountResponses = savingAccountEntityList.stream()
                    .map(SavingAccountResponse::fromSavingAccount)
                    .toList();

            SavingAccountListResponse savingAccountListResponse = SavingAccountListResponse
                    .builder()
                    .savingAccounts(savingAccountResponses)
                    .build();

            //return response
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
