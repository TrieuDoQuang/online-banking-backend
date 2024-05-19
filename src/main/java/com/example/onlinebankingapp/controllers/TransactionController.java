package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.PaymentAccountDTO;
import com.example.onlinebankingapp.dtos.TransactionDTO;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.services.Transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    @PostMapping("/makeTransaction")
    public ResponseEntity<?> makeTransaction(
            @Valid @RequestBody TransactionDTO transactionDTO
    ) {
        try {
            transactionService.makeTransaction(transactionDTO);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Transaction Completed!")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
