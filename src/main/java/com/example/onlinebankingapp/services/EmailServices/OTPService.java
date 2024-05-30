package com.example.onlinebankingapp.services.EmailServices;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPService {

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public String generateOtp(String email) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000); // Generate a 6 digit OTP
        otpStorage.put(email, otp);
        return otp;
    }

    public boolean verifyOtp(String email, String otp) throws Exception {

        if (!otp.equals(otpStorage.get(email))) {
            throw new Exception("Invalid OTP");
        }

        return otp.equals(otpStorage.get(email));
    }
}