package com.example.onlinebankingapp.components;

import com.example.onlinebankingapp.entities.TokenEntity;
import com.example.onlinebankingapp.exceptions.InvalidParamException;
import com.example.onlinebankingapp.repositories.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import com.example.onlinebankingapp.entities.CustomerEntity;

import java.security.Key;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;

/* In charge: Trieu */
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    // Injecting values from properties file
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    @Value("${jwt.secretKey}")
    private String secretKey;

    // Logger for logging events
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);
    // Repository for token-related operations
    private final TokenRepository tokenRepository;


    // Method to generate a JWT token for a given customer
    /* In charge: Trieu */
    public String generateToken(com.example.onlinebankingapp.entities.CustomerEntity customer) throws Exception{
        //properties => claims
        // Create claims for the token
        Map<String, Object> claims = new HashMap<>();
        // Add subject identifier (phone number or email)
        String subject = getSubject(customer);
        claims.put("subject", subject);
        // Add user ID
        claims.put("customerId", customer.getId());
        try {
            // Generate the token using claims and sign it with the secret key
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e) {
            //you can "inject" Logger, instead System.out.println
            // Throw an exception if token generation fails
            throw new InvalidParamException("Cannot create jwt token, error: "+e.getMessage());
            //return null;
        }
    }

    // Method to determine the subject identifier (phone number or email) for a customer
    /* In charge: Trieu */
    private static String getSubject(CustomerEntity customer) {
        // Determine subject identifier (phone number or email)
        String subject = customer.getPhoneNumber();
        if (subject == null || subject.isBlank()) {
            // If phone number is null or blank, use email as subject
            subject = customer.getEmail();
        }
        return subject;
    }


    // Method to retrieve the signing key used for JWT tokens
    /* In charge: Trieu */
    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        //Keys.hmacShaKeyFor(Decoders.BASE64.decode("TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI="));
        return Keys.hmacShaKeyFor(bytes);
    }

    // Method to generate a secret key
    /* In charge: Trieu */
    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }

    // Method to extract all claims from a JWT token
    /* In charge: Trieu */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Method to extract a specific claim from a JWT token
    /* In charge: Trieu */
    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Method to check if a JWT token is expired
    //check expiration
    /* In charge: Trieu */
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    // Method to get the subject from a JWT token
    /* In charge: Trieu */
    public String getSubject(String token) {
        return  extractClaim(token, Claims::getSubject);
    }

    // Method to validate a JWT token
    /* In charge: Trieu */
    public boolean validateToken(String token, CustomerEntity customer) {
        try {
            String subject = extractClaim(token, Claims::getSubject);
            //subject is phoneNumber or email
            TokenEntity existingToken = tokenRepository.findByToken(token);
            if(existingToken == null ||
                    existingToken.isRevoked() == true
//                    !customer.isActive()
            ) {
                return false;
            }
            return (subject.equals(customer.getEmail()))
                    && !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
