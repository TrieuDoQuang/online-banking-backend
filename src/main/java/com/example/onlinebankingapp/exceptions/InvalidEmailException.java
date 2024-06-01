package com.example.onlinebankingapp.exceptions;

// Custom exception class for invalid email scenarios
// in charge: trieu
public class InvalidEmailException extends  Exception{
    // Constructor to initialize the exception with a custom message
    public InvalidEmailException(String message) {
        super(message);
    }

}
