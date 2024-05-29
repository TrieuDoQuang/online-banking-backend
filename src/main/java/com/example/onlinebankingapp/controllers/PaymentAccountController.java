package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.PaymentAccountDTO;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.responses.Customer.CustomerResponse;
import com.example.onlinebankingapp.responses.PaymentAccount.PaymentAccountListResponse;
import com.example.onlinebankingapp.responses.PaymentAccount.PaymentAccountResponse;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.services.PaymentAccount.PaymentAccountService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/paymentAccounts")
@RequiredArgsConstructor
public class PaymentAccountController {


    private final PaymentAccountService paymentAccountService;

    @PostMapping("")
    public ResponseEntity<?> insertPaymentAccount(
            @Valid @RequestBody PaymentAccountDTO paymentAccountDTO
    ) {
        try {
            PaymentAccountEntity paymentAccountResponse = paymentAccountService.insertPaymentAccount(paymentAccountDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .result(PaymentAccountResponse.fromPaymentAccount(paymentAccountResponse))
                            .message("Insert an Payment Account successfully!")
                            .status(HttpStatus.OK)
                            .build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/setDefaultAccount")
    public ResponseEntity<?> setDefaultPaymentAccount(
            @Valid @RequestBody PaymentAccountDTO paymentAccountDTO
    ) {
        try {
            paymentAccountService.setDefaultPaymentAccount(paymentAccountDTO.getCustomerId(), paymentAccountDTO.getAccountNumber());
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Set Default Payment Account Successfully!")
                            .status(HttpStatus.OK)
                            .build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllPaymentAccounts() {
        try {
            List<PaymentAccountEntity> paymentAccounts = paymentAccountService.getAllPaymentAccounts();

            List<PaymentAccountResponse> paymentAccountResponse = paymentAccounts.stream()
                    .map(PaymentAccountResponse::fromPaymentAccount)
                    .toList();

            PaymentAccountListResponse paymentAccountListResponse = PaymentAccountListResponse
                    .builder()
                    .paymentAccounts(paymentAccountResponse)
                    .build();

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Get Payment Account list successfully")
                    .status(HttpStatus.OK)
                    .result(paymentAccountListResponse)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getDefaultAccount/{id}")
    public ResponseEntity<?> getDefaultPaymentAccount(@Valid @PathVariable("id") Long customerId) {
        try {

            PaymentAccountEntity paymentAccount = paymentAccountService.getDefaultPaymentAccount(customerId);

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Get Payment Account list successfully")
                    .status(HttpStatus.OK)
                    .result(PaymentAccountResponse.fromPaymentAccount(paymentAccount))
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentAccountById(@Valid @PathVariable("id") Long paymentAccountId) {
        try {
            PaymentAccountEntity existingPaymentAccount = paymentAccountService.getPaymentAccountById(paymentAccountId);
            return ResponseEntity.ok(ResponseObject.builder()
                    .result(PaymentAccountResponse.fromPaymentAccount(existingPaymentAccount))
                    .message("Get Payment Account successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getByAccountNumber/{accountNumber}")
    public ResponseEntity<?> getCustomerByAccountNumber(@Valid @PathVariable("accountNumber") String accountNumber) {
        try {
            PaymentAccountEntity existingPaymentAccount = paymentAccountService.getPaymentAccountByAccountNumber(accountNumber);
            return ResponseEntity.ok(ResponseObject.builder()
                    .result(CustomerResponse.fromCustomerResponse(existingPaymentAccount.getCustomer()))
                    .message("Get Payment Account successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/getPaymentAccounts/{id}")
    public ResponseEntity<?> getPaymentAccountsByCustomerId(@Valid @PathVariable("id") Long customerId){
        try {
            List<PaymentAccountEntity> paymentAccounts = paymentAccountService.getPaymentAccountsByCustomerId(customerId);

            List<PaymentAccountResponse> paymentAccountResponse = paymentAccounts.stream()
                    .map(PaymentAccountResponse::fromPaymentAccount)
                    .toList();

            PaymentAccountListResponse paymentAccountListResponse = PaymentAccountListResponse
                    .builder()
                    .paymentAccounts(paymentAccountResponse)
                    .build();

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Get Payment Account list by CustomerID successfully")
                    .status(HttpStatus.OK)
                    .result(paymentAccountListResponse)
                    .build());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
