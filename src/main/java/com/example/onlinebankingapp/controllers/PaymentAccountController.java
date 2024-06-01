package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.AmountOperationDTO;
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

    //end point for inserting a payment account
    //in charge: Dat
    @PostMapping("")
    public ResponseEntity<?> insertPaymentAccount(
            @Valid @RequestBody PaymentAccountDTO paymentAccountDTO
    ) {
        try {
            //inserting a payment account
            PaymentAccountEntity paymentAccountResponse = paymentAccountService.insertPaymentAccount(paymentAccountDTO);

            //return response
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

    //end point for setting an account as the default account
    //in charge: Dat
    @PostMapping("/setDefaultAccount")
    public ResponseEntity<?> setDefaultPaymentAccount(
            @Valid @RequestBody PaymentAccountDTO paymentAccountDTO
    ) {
        try {
            //set the requested account as default account
            paymentAccountService.setDefaultPaymentAccount(paymentAccountDTO.getCustomerId(), paymentAccountDTO.getAccountNumber());

            //return response
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Set Default Payment Account Successfully!")
                            .status(HttpStatus.OK)
                            .build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for getting all the payment accounts
    //in charge: Dat
    @GetMapping("")
    public ResponseEntity<?> getAllPaymentAccounts() {
        try {
            //get all the payment accounts
            List<PaymentAccountEntity> paymentAccounts = paymentAccountService.getAllPaymentAccounts();

            List<PaymentAccountResponse> paymentAccountResponse = paymentAccounts.stream()
                    .map(PaymentAccountResponse::fromPaymentAccount)
                    .toList();

            PaymentAccountListResponse paymentAccountListResponse = PaymentAccountListResponse
                    .builder()
                    .paymentAccounts(paymentAccountResponse)
                    .build();

            //return response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Get Payment Account list successfully")
                    .status(HttpStatus.OK)
                    .result(paymentAccountListResponse)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for getting the default payment account of a customer
    //in charge: Dat
    @GetMapping("/getDefaultAccount/{id}")
    public ResponseEntity<?> getDefaultPaymentAccount(@Valid @PathVariable("id") Long customerId) {
        try {
            //get the default account
            PaymentAccountEntity paymentAccount = paymentAccountService.getDefaultPaymentAccount(customerId);

            //return the account in response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Get Payment Account list successfully")
                    .status(HttpStatus.OK)
                    .result(PaymentAccountResponse.fromPaymentAccount(paymentAccount))
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for getting a payment account by its id
    //in charge: Dat
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentAccountById(@Valid @PathVariable("id") Long paymentAccountId) {
        try {
            //get the payment account by id
            PaymentAccountEntity existingPaymentAccount = paymentAccountService.getPaymentAccountById(paymentAccountId);

            //return the account in response
            return ResponseEntity.ok(ResponseObject.builder()
                    .result(PaymentAccountResponse.fromPaymentAccount(existingPaymentAccount))
                    .message("Get Payment Account successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for getting a customer by the payment account number
    //in charge: Trieu
    @GetMapping("/getByAccountNumber/{accountNumber}")
    public ResponseEntity<?> getCustomerByAccountNumber(@Valid @PathVariable("accountNumber") String accountNumber) {
        try {
            //get the customer by the payment accoutn number
            PaymentAccountEntity existingPaymentAccount = paymentAccountService.getPaymentAccountByAccountNumber(accountNumber);

            //return result in response
            return ResponseEntity.ok(ResponseObject.builder()
                    .result(CustomerResponse.fromCustomerResponse(existingPaymentAccount.getCustomer()))
                    .message("Get Payment Account successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for getting all payments accounts of a customer
    //in charge: Trieu
    @GetMapping("/getPaymentAccounts/{id}")
    public ResponseEntity<?> getPaymentAccountsByCustomerId(@Valid @PathVariable("id") Long customerId){
        try {
            // Retrieve payment accounts for the given customer ID
            List<PaymentAccountEntity> paymentAccounts = paymentAccountService.getPaymentAccountsByCustomerId(customerId);

            //Build response
            List<PaymentAccountResponse> paymentAccountResponse = paymentAccounts.stream()
                    .map(PaymentAccountResponse::fromPaymentAccount)
                    .toList();

            PaymentAccountListResponse paymentAccountListResponse = PaymentAccountListResponse
                    .builder()
                    .paymentAccounts(paymentAccountResponse)
                    .build();

            // Return a successful response with the payment account data
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

    //end point for topping up a payment account
    //in charge: Trieu
    @PutMapping("/topUpPaymentAccount/{id}")
    public ResponseEntity<?> topUpPaymentAccount(
            @Valid @PathVariable("id") Long paymentAccountId , @Valid @RequestBody AmountOperationDTO amountDTO) {
        try {
            // Check if the top-up amount exceeds the maximum allowable limit
            if (amountDTO.getAmount() > 5000000) {
                return ResponseEntity.status(888).body(ResponseObject.builder()
                        .message("Amount exceeds the maximum allowable limit.")
                        .status(HttpStatus.BAD_REQUEST)
                        .result(amountDTO)
                        .build());
            }

            // Perform the top-up operation for the specified payment account
            paymentAccountService.topUpPaymentAccount(paymentAccountId, amountDTO);

            // Return a successful response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Top up Payment Account Successfully")
                    .status(HttpStatus.OK)
                    .result(amountDTO)
                    .build());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //end point for withdrawing money from a payment account
    //in charge: Trieu
    @PutMapping("/withdrawPaymentAccount/{id}")
    public ResponseEntity<?> withdrawPaymentAccount(
            @Valid @PathVariable("id") Long paymentAccountId, @Valid @RequestBody AmountOperationDTO amountDTO ) {
        try {
            // Check if the withdrawal amount exceeds the maximum allowable limit
            if (amountDTO.getAmount() > 5000000) {
                return ResponseEntity.status(888).body(ResponseObject.builder()
                        .message("Amount exceeds the maximum allowable limit.")
                        .status(HttpStatus.BAD_REQUEST)
                        .result(amountDTO)
                        .build());
            }

            // Perform the withdrawal operation for the specified payment account
            paymentAccountService.withdrawPaymentAccount(paymentAccountId, amountDTO);

            // Return a successful response
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Withdraw Payment Account Successfully")
                    .status(HttpStatus.OK)
                    .result(amountDTO)
                    .build());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
