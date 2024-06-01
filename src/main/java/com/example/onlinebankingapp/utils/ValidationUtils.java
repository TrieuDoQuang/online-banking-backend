package com.example.onlinebankingapp.utils;

import java.util.regex.Pattern;

//in charge: Trieu
public class ValidationUtils {
    // Method to validate an email address
    public static boolean isValidEmail(String email) {
        // Regular expression pattern for validating email addresses
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // Create a Pattern object
        Pattern pattern = Pattern.compile(emailRegex);
        // Match the input email with the pattern
        return email != null && pattern.matcher(email).matches();
    }

    // Method to validate a password
    public static boolean isValidPassword(String password) {
        // Check if the password is between 8 and 20 characters long
        if (password == null || password.length() < 8 || password.length() > 20) {
            return false;
        }

        // Regular expression to check for at least one digit, one letter, and one special character
        String digitRegex = ".*\\d.*";
        String letterRegex = ".*[a-zA-Z].*";
        String specialCharRegex = ".*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?].*";

        // Check if the password matches the regular expressions
        boolean hasDigit = password.matches(digitRegex);
        boolean hasLetter = password.matches(letterRegex);
        boolean hasSpecialChar = password.matches(specialCharRegex);

        // Return true if the password satisfies all conditions, false otherwise
        return hasDigit && hasLetter && hasSpecialChar;
    }



}
