package com.example.onlinebankingapp.exceptions;

// Custom exception class for invalid password scenarios
//in charge: trieu
public class InvalidPasswordException extends Exception {

    // Constructor to initialize the exception with a custom message
    public InvalidPasswordException(String message) {
        super(message);
    }
}
