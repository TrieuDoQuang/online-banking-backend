package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.PaymentAccountDTO;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.responses.PaymentAccount.PaymentAccountListResponse;
import com.example.onlinebankingapp.responses.PaymentAccount.PaymentAccountResponse;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.services.PaymentAccount.PaymentAccountService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
                            .message("Thêm tài khoản thành công!")
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
                    .message("Get PaymentAccount list successfully")
                    .status(HttpStatus.OK)
                    .result(paymentAccountListResponse)
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
                    .message("Get payment account successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
