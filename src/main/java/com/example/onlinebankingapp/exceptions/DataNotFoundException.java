package com.example.onlinebankingapp.exceptions;

// Custom exception class for data not found scenarios
// in charge: Trieu
public class DataNotFoundException extends Exception{
    // Constructor to initialize the exception with a custom message
    public DataNotFoundException(String message) {
        super(message);
    }

}
