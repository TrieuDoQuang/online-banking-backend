package com.example.onlinebankingapp.services.Customer;

import com.example.onlinebankingapp.dtos.CustomerDTO;
import com.example.onlinebankingapp.dtos.CustomerLoginDTO;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.dtos.ChangePasswordCustomerDTO;
import com.example.onlinebankingapp.exceptions.InvalidEmailException;
import com.example.onlinebankingapp.exceptions.InvalidPasswordException;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.List;

/**
 * Interface defining abstract functions for Customer Service.
 * This service manages operations related to customers.
 * Responsible: Dat & Trieu
 */
public interface CustomerService {
    /**
     * Validates the customer's login credentials and generates a JWT token.
     *
     * @param customerLoginDTO The DTO containing customer login information.
     * @return A JWT token if the login is successful.
     * @throws Exception           If an unexpected error occurs during the login process.
     * @throws InvalidEmailException If the email format is invalid.
     */
    String login(CustomerLoginDTO customerLoginDTO) throws Exception, InvalidEmailException;

    /**
     * Registers a new customer with the provided details.
     *
     * @param customerDTO The DTO containing customer registration information.
     * @return The registered customer entity.
     * @throws DataNotFoundException If there is an issue with the data needed for registration.
     * @throws ParseException        If there is an error parsing the data.
     * @throws InvalidPasswordException If the password format is invalid.
     * @throws InvalidEmailException If the email format is invalid.
     */
    CustomerEntity insertCustomer(CustomerDTO customerDTO) throws DataNotFoundException, ParseException, ParseException, InvalidPasswordException, InvalidEmailException;


    /**
     * Retrieves customer details using the provided JWT token.
     *
     * @param token The JWT token containing customer information.
     * @return The customer entity associated with the token.
     * @throws Exception If an unexpected error occurs during token processing.
     */
    CustomerEntity getCustomerDetailsFromToken(String token) throws Exception;

    /**
     * Retrieves customer details using the provided Refresh token.
     *
     * @param token The Refresh token containing customer information.
     * @return The customer entity associated with the Refresh token.
     * @throws Exception If an unexpected error occurs during token processing.
     */
    CustomerEntity getCustomerDetailsFromRefreshToken(String token) throws Exception;

    /**
     * Retrieves details of all customers.
     *
     * @return A list of all customer entities.
     * @throws Exception If there is an error retrieving customer data.
     */
    List<CustomerEntity> getAllCustomers() throws Exception;

    /**
     * Retrieves customer details by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The customer entity with the specified ID.
     * @throws Exception If there is an error retrieving customer data.
     */
    CustomerEntity getCustomerById(long id) throws Exception;

    /**
     * Loads customer details by their phone number.
     *
     * @param phoneNumber The phone number of the customer to retrieve.
     * @return The customer entity with the specified phone number.
     * @throws DataNotFoundException If the customer with the given phone number is not found.
     */
    CustomerEntity loadCustomerByPhoneNumber(String phoneNumber) throws DataNotFoundException;

    /**
     * Changes the password of the customer with the specified ID.
     *
     * @param customerId   The ID of the customer whose password is to be changed.
     * @param customerDTO The DTO containing the new password.
     * @throws DataNotFoundException If the customer with the given ID is not found.
     * @throws InvalidPasswordException If the new password format is invalid.
     */
    void changePassword(long customerId, ChangePasswordCustomerDTO customerDTO) throws DataNotFoundException, InvalidPasswordException;
}
