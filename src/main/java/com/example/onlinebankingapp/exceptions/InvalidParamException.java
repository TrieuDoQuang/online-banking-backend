package com.example.onlinebankingapp.exceptions;

// Custom exception class for invalid parameter scenarios
// in charge: trieu
public class InvalidParamException extends Exception {

    // Constructor to initialize the exception with a custom message
    public InvalidParamException(String message) {
        super(message);
    }
}
