package com.example.onlinebankingapp.dtos;

import lombok.Data;

@Data
public class OTPVerificationRequest {


    private String receiverEmail;
    private String otp;
    private TransactionDTO transactionDTO;

}
