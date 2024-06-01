package com.example.onlinebankingapp.services.EmailServices;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for generating and verifying OTPs (One-Time Passwords).
 */
@Service
public class OTPService {

    // Storage for OTPs mapped to email addresses
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    /**
     * Generates a 6-digit OTP for the specified email address.
     *
     * @param email The email address for which to generate the OTP.
     * @return The generated OTP.
     */
    //in charge: dat
    public String generateOtp(String email) {
        // Generate a random 6-digit OTP
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000); // Generate a 6 digit OTP
        // Store the OTP in the storage map with the email as the key
        otpStorage.put(email, otp);
        return otp;
    }

    /**
     * Verifies the provided OTP for the specified email address.
     *
     * @param email The email address for which to verify the OTP.
     * @param otp The OTP to verify.
     * @return True if the OTP is valid for the specified email, false otherwise.
     * @throws Exception If the OTP verification fails.
     */
    //in charge: dat
    public boolean verifyOtp(String email, String otp) throws Exception {

        // Check if the provided OTP matches the one stored for the email
        if (!otp.equals(otpStorage.get(email))) {
            throw new Exception("Invalid OTP");
        }

        // If the OTP is valid, return true
        return otp.equals(otpStorage.get(email));
    }
}