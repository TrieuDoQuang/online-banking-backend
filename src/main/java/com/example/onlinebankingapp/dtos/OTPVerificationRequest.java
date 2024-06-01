package com.example.onlinebankingapp.dtos;

import lombok.Data;
//in charge: dat
@Data
public class OTPVerificationRequest //dto for otp verification request
{


    private String receiverEmail;
    private String otp;
    private TransactionDTO transactionDTO;

}
