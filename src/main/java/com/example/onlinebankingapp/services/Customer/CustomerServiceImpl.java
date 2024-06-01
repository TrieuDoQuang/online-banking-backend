package com.example.onlinebankingapp.services.Customer;

import com.example.onlinebankingapp.components.JwtTokenUtils;
import com.example.onlinebankingapp.dtos.ChangePasswordCustomerDTO;
import com.example.onlinebankingapp.dtos.CustomerDTO;
import com.example.onlinebankingapp.dtos.CustomerLoginDTO;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.TokenEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.exceptions.ExpiredTokenException;
import com.example.onlinebankingapp.exceptions.InvalidEmailException;
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
import static com.example.onlinebankingapp.utils.ValidationUtils.isValidPassword;
import static java.lang.String.format;
import static java.lang.String.valueOf;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtil;
    private final TokenRepository tokenRepository;

    // Implementation of the login functionality
    //in charge: trieu
    @Override
    public String login(CustomerLoginDTO customerLoginDTO) throws Exception, InvalidEmailException {
        Optional <CustomerEntity> optionalCustomer = Optional.empty();
        String subject = null;

        // Check if the email is in a valid format
        if (!isValidEmail(customerLoginDTO.getEmail())){
            throw new InvalidEmailException("The format of email is not available!");
        }

        // Retrieve customer by email if available
        if (optionalCustomer.isEmpty() && customerLoginDTO.getEmail() != null) {
            optionalCustomer = customerRepository.findByEmail(customerLoginDTO.getEmail());
            subject = customerLoginDTO.getEmail();
        }

        // Check if the customer exists and the password matches
        CustomerEntity existingUser = optionalCustomer.get();

        if(!passwordEncoder.matches(customerLoginDTO.getPassword(), existingUser.getPassword())) {
            throw new InvalidPasswordException("WRONG PASSWORD");

        }
        // Authenticate user and generate JWT token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                subject, customerLoginDTO.getPassword(),
                existingUser.getAuthorities()
        );

        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);

    }


    // Implementation of inserting a new customer + registering a new account to log in
    //in charge: trieu
    @Override
    public CustomerEntity insertCustomer(CustomerDTO customerDTO) throws DataNotFoundException, ParseException, InvalidPasswordException, InvalidEmailException {
        String email = customerDTO.getEmail();
        String citizenId = customerDTO.getCitizenId();
        String password = customerDTO.getPassword();
        String phoneNumber = customerDTO.getPhoneNumber();

        // Validate password format
        if(!isValidPassword(password)) {
            throw new InvalidPasswordException("Password must satisfy the following conditions:\n" +
                    "The length must be from 8 to 20 characters\n" +
                    "Contains at least 01 digit, 01 letter and 01 special character");
        };

        // Validate email format
        if (!isValidEmail(email)){
            throw new InvalidEmailException("The format of email is not available!");
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(password);

        // Check for existing customers with the same email, citizen ID, or phone number
        if (customerRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("Exist customer with email");
        }
        if (customerRepository.existsByCitizenId(citizenId)) {
            throw new DataIntegrityViolationException("Exist customer with citizen id");
        }
        if(customerRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Exist customer with phone number");
        }

        if (customerRepository.existsByEmailAndCitizenId(email, citizenId)) {
            throw new DataIntegrityViolationException("Exists customer account!");
        }

        // Create new CustomerEntity instance
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

    //get customer details from token
    //in charge: trieu
    @Override
    public CustomerEntity getCustomerDetailsFromToken(String token) throws Exception {
        // Check if the token has expired
        if(jwtTokenUtil.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }

        // Extract the subject (phone number or email) from the token
        String subject = jwtTokenUtil.getSubject(token);
        Optional<CustomerEntity> customer;

        // Try to find the customer by phone number
        customer = customerRepository.findByPhoneNumber(subject);

        // If customer not found by phone number and subject is a valid email, try finding by email
        if (customer.isEmpty() && isValidEmail(subject)) {
            customer = customerRepository.findByEmail(subject);

        }
        // Return the customer if found, otherwise throw an exception
        return customer.orElseThrow(() -> new Exception("Customer not found"));
    }

    //get customer details from refresh token
    //in charge: trieu
    @Override
    public CustomerEntity getCustomerDetailsFromRefreshToken(String refreshToken) throws Exception {
//        logger.debug("Searching for refresh token: {}", refreshToken);

        // Find the token entity corresponding to the refresh token
        TokenEntity existingToken = tokenRepository.findByRefreshToken(refreshToken);

        // If refresh token not found, throw an exception
        if (existingToken == null) {
//            logger.error("Refresh token not found: {}", refreshToken);
            throw new Exception("Refresh token not found");
        }

//        logger.debug("Token found: {}", existingToken.getToken());

        // Retrieve customer details using the token associated with the refresh token
        return getCustomerDetailsFromToken(existingToken.getToken());
    }

    // Retrieve all customers from the repository
    //in charge: dat
    @Override
    public List<CustomerEntity> getAllCustomers() throws Exception {
        return customerRepository.findAll();
    }

    // find customer by its id
    // in charge: dat
    @Override
    public CustomerEntity getCustomerById(long id) throws Exception {

        // Find customer by ID
        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);

        // If customer exists, return it, otherwise throw an exception
        if(optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        }

        throw new Exception("Cannot find Customer with id: " + id);
    }

    @Override
    public CustomerEntity loadCustomerByPhoneNumber(String phoneNumber) throws DataNotFoundException {
        return null;
    }

    //change user password
    //in charge: trieu
    @Override
    public void changePassword(long customerId, ChangePasswordCustomerDTO customerDTO) throws DataNotFoundException, InvalidPasswordException {
        // Retrieve existing customer by ID, throw an exception if not found
        Optional<CustomerEntity> existingCustomer = Optional.ofNullable(customerRepository.findById(customerId)
                .orElseThrow(() -> new DataNotFoundException("Customer not found!")));
        CustomerEntity customerChangePassword = existingCustomer.get();
        // Check if the current password matches
        if (!passwordEncoder.matches(customerDTO.getPassword(), customerChangePassword.getPassword())){
            throw new InvalidPasswordException("WRONG PASSWORD");
        }

        // Check if the new password matches the confirmed password and meets criteria
        if (!(customerDTO.getNewPassword().matches(customerDTO.getConfirmPassword()))) {
            throw new InvalidPasswordException("New password and confirm password do not match!");
        }
        if(!isValidPassword(customerDTO.getNewPassword())) {
            throw new InvalidPasswordException("Password must satisfy the following conditions:\n" +
                    "The length must be from 8 to 20 characters\n" +
                    "Contains at least 01 digit, 01 letter and 01 special character");
        };

        // Encode and set the new password
        customerChangePassword.setPassword(passwordEncoder.encode(customerDTO.getNewPassword()));

        // Save the updated customer
        customerRepository.save(customerChangePassword);

        // Delete all tokens associated with this customer
        List<TokenEntity> tokens = tokenRepository.findByCustomer(customerChangePassword);
        for (TokenEntity token : tokens) {
            tokenRepository.delete(token);
        }

    }
}
