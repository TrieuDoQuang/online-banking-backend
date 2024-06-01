package com.example.onlinebankingapp.exceptions;

// Custom exception class for expired token scenarios
// in charge: trieu
public class ExpiredTokenException extends Exception{
    // Constructor to initialize the exception with a custom message
    public ExpiredTokenException(String message) {
        super(message);
    }

}
