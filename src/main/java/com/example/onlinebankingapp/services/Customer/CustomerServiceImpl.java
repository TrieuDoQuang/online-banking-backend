package com.example.onlinebankingapp.services.Customer;

import com.example.onlinebankingapp.components.JwtTokenUtils;
import com.example.onlinebankingapp.dtos.CustomerDTO;
import com.example.onlinebankingapp.dtos.CustomerLoginDTO;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.TokenEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.exceptions.ExpiredTokenException;
import com.example.onlinebankingapp.exceptions.InvalidPasswordException;
import com.example.onlinebankingapp.repositories.CustomerRepository;
import com.example.onlinebankingapp.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.onlinebankingapp.utils.ValidationUtils.isValidEmail;
import static java.lang.String.valueOf;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtil;
    private final TokenRepository tokenRepository;

    @Override
    public String login(CustomerLoginDTO customerLoginDTO) throws Exception {
        Optional <CustomerEntity> optionalCustomer = Optional.empty();
        String subject = null;

        if (optionalCustomer.isEmpty() && customerLoginDTO.getEmail() != null) {
            optionalCustomer = customerRepository.findByEmail(customerLoginDTO.getEmail());
            subject = customerLoginDTO.getEmail();
        }
        CustomerEntity existingUser = optionalCustomer.get();

        if(!passwordEncoder.matches(customerLoginDTO.getPassword(), existingUser.getPassword())) {
            throw new InvalidPasswordException("WRONG PASSWORD");

        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                subject, customerLoginDTO.getPassword(),
                existingUser.getAuthorities()
        );

        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);

    }


    @Override
    public CustomerEntity insertCustomer(CustomerDTO customerDTO) throws DataNotFoundException, ParseException {
        String email = customerDTO.getEmail();
        String citizenId = customerDTO.getCitizenId();
        String password = customerDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        if (customerRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("Exist customer with email");
        }
        if (customerRepository.existsByCitizenId(citizenId)) {
            throw new DataIntegrityViolationException("Exist customer with citizen id");
        }

        if (customerRepository.existsByEmailAndCitizenId(email, citizenId)) {
            throw new DataIntegrityViolationException("Exists customer account!");
        }

        java.sql.Date dateOfBirth = new java.sql.Date(customerDTO.getDateOfBirth().getTime());

        CustomerEntity newCustomer = CustomerEntity.builder()
                .name(customerDTO.getName())
                .password(encodedPassword)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .address(customerDTO.getAddress())
                .phoneNumber(valueOf(customerDTO.getPhoneNumber()))
                .citizenId(valueOf(citizenId))
                .pinNumber(customerDTO.getPinNumber())
                .build();

        return customerRepository.save(newCustomer);
    }

    @Override
    public CustomerEntity getCustomerDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String subject = jwtTokenUtil.getSubject(token);
        Optional<CustomerEntity> customer;

        customer = customerRepository.findByPhoneNumber(subject);

        if (customer.isEmpty() && isValidEmail(subject)) {
            customer = customerRepository.findByEmail(subject);

        }
        return customer.orElseThrow(() -> new Exception("Customer not found"));
    }

    @Override
    public CustomerEntity getCustomerDetailsFromRefreshToken(String refreshToken) throws Exception {
//        logger.debug("Searching for refresh token: {}", refreshToken);

        TokenEntity existingToken = tokenRepository.findByRefreshToken(refreshToken);

        if (existingToken == null) {
//            logger.error("Refresh token not found: {}", refreshToken);
            throw new Exception("Refresh token not found");
        }

//        logger.debug("Token found: {}", existingToken.getToken());

        return getCustomerDetailsFromToken(existingToken.getToken());
    }





    @Override
    public List<CustomerEntity> getAllCustomers() throws Exception {
        return null;
    }

    @Override
    public CustomerEntity getCustomerById(long id) throws Exception {
        return null;
    }

    @Override
    public CustomerEntity loadCustomerByPhoneNumber(String phoneNumber) throws DataNotFoundException {
        return null;
    }
}
