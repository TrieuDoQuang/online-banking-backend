package com.example.onlinebankingapp.services.Token;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.TokenEntity;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    TokenEntity addToken(CustomerEntity customer, String token, boolean isMobileDevice);
    TokenEntity refreshToken(String refreshToken, CustomerEntity customer) throws Exception;
}
