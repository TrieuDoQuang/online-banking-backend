package com.example.onlinebankingapp.services.Token;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.TokenEntity;
import org.springframework.stereotype.Service;

//interface defining abstract functions for processing token
//in charge: trieu
@Service
public interface TokenService {
    // Add a new token for the given customer
    TokenEntity addToken(CustomerEntity customer, String token, boolean isMobileDevice);

    // Refresh an existing token using the provided refresh token and customer entity
    // It may throw an exception if there is an issue during token refreshing process
    TokenEntity refreshToken(String refreshToken, CustomerEntity customer) throws Exception;
}
