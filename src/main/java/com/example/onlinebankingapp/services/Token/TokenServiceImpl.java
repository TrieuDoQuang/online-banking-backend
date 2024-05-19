package com.example.onlinebankingapp.services.Token;
import org.springframework.stereotype.Service;
import com.example.onlinebankingapp.repositories.TokenRepository;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.TokenEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.exceptions.ExpiredTokenException;
import com.example.onlinebankingapp.components.JwtTokenUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private static final int MAX_TOKENS = 3;
    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    private final TokenRepository tokenRepository;
    private final JwtTokenUtils jwtTokenUtil;
    @Transactional
    @Override
    public TokenEntity refreshToken(String refreshToken, CustomerEntity customer) throws Exception{
        TokenEntity existingToken = tokenRepository.findByRefreshToken(refreshToken);
        if(existingToken == null) {
            throw new DataNotFoundException("Refresh token does not exist");
        }
        if(existingToken.getRefreshExpirationDate().compareTo(LocalDateTime.now()) < 0){
            tokenRepository.delete(existingToken);
            throw new ExpiredTokenException("Refresh token is expired");
        }
        String token = jwtTokenUtil.generateToken(customer);
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);
        existingToken.setExpirationDate(expirationDateTime);
        existingToken.setToken(token);
        existingToken.setRefreshToken(UUID.randomUUID().toString());
        existingToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));
        return existingToken;
    }
    @Transactional
    @Override
    public TokenEntity addToken(CustomerEntity customer,String token, boolean isMobileDevice) {
        List<TokenEntity> userTokens = tokenRepository.findByCustomer(customer);
        int tokenCount = userTokens.size();
        // Số lượng token vượt quá giới hạn, xóa một token cũ
        if (tokenCount >= MAX_TOKENS) {
            //kiểm tra xem trong danh sách userTokens có tồn tại ít nhất
            //một token không phải là thiết bị di động (non-mobile)
            boolean hasNonMobileToken = !userTokens.stream().allMatch(TokenEntity::isMobile);
            TokenEntity tokenToDelete;
            if (hasNonMobileToken) {
                tokenToDelete = userTokens.stream()
                        .filter(userToken -> !userToken.isMobile())
                        .findFirst()
                        .orElse(userTokens.get(0));
            } else {
                //tất cả các token đều là thiết bị di động,
                //chúng ta sẽ xóa token đầu tiên trong danh sách
                tokenToDelete = userTokens.get(0);
            }
            tokenRepository.delete(tokenToDelete);
        }
        long expirationInSeconds = expiration;
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expirationInSeconds);
        // Tạo mới một token cho người dùng
        TokenEntity newToken = TokenEntity.builder()
                .customer(customer)
                .token(token)
                .revoked(false)
                .expired(false)
                .tokenType("Bearer")
                .expirationDate(expirationDateTime)
                .isMobile(isMobileDevice)
                .build();

        newToken.setRefreshToken(UUID.randomUUID().toString());
        newToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));
        tokenRepository.save(newToken);
        return newToken;
    }
}
