package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.OTPRequest;
import com.example.onlinebankingapp.dtos.OTPVerificationRequest;
import com.example.onlinebankingapp.dtos.TransactionDTO;
import com.example.onlinebankingapp.entities.TransactionEntity;
import com.example.onlinebankingapp.responses.PaymentAccount.PaymentAccountListResponse;
import com.example.onlinebankingapp.responses.PaymentAccount.PaymentAccountResponse;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.responses.Transaction.TransactionListResponse;
import com.example.onlinebankingapp.responses.Transaction.TransactionResponse;
import com.example.onlinebankingapp.services.EmailServices.EmailService;
import com.example.onlinebankingapp.services.EmailServices.OTPService;
import com.example.onlinebankingapp.services.Transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final OTPService otpService;
    private final EmailService emailService;


    // Endpoint for retrieving transactions history by customer ID
    // In charge: Dat
    @GetMapping("/getByCustomerId/{id}")
    public ResponseEntity<?> getTransactionsByCustomerId(@Valid @PathVariable("id") Long customerId) {
        try {
            //call service layer to get all transactions of a customer
            List<TransactionEntity> transactions = transactionService.getTransactionsByCustomerId(customerId);

            //creating response
            List<TransactionResponse> transactionResponses = transactions.stream()
                    .map(TransactionResponse::fromTransaction)
                    .toList();

            TransactionListResponse transactionListResponse = TransactionListResponse
                    .builder()
                    .transactions(transactionResponses)
                    .build();

            //return response
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Get Transactions History Successfully")
                            .result(transactionListResponse)
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for sending OTP to a specified email address
    // In charge: Dat
    @PostMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody OTPRequest request) {
        try {
            //call service layer to generate and send otp
            String otp = otpService.generateOtp(request.getReceiverEmail());
            emailService.sendOtpEmail(request.getReceiverEmail(), otp);

            //return response
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("OTP sent to email!")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for verifying OTP and making a transaction
    // In charge: Dat
    @PostMapping("/verifyOtpAndMakeTransaction")
    public ResponseEntity<?> verifyOtpAndMakeTransaction(@RequestBody OTPVerificationRequest request) {
        try {
            //call service layer to verify otp
            boolean isValid = otpService.verifyOtp(request.getReceiverEmail(), request.getOtp());

            //return response based on validation result
            if (isValid) {
                transactionService.makeTransaction(request.getTransactionDTO());
                return ResponseEntity.ok(
                        ResponseObject.builder()
                                .message("Transaction Completed!")
                                .status(HttpStatus.OK)
                                .build());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
