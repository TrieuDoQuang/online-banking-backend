package com.example.onlinebankingapp.services.Customer;

import com.example.onlinebankingapp.dtos.CustomerDTO;
import com.example.onlinebankingapp.dtos.CustomerLoginDTO;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import com.example.onlinebankingapp.dtos.ChangePasswordCustomerDTO;
import com.example.onlinebankingapp.exceptions.InvalidPasswordException;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.List;

public interface CustomerService {

    String login(CustomerLoginDTO customerLoginDTO) throws Exception;

    CustomerEntity insertCustomer(CustomerDTO customerDTO) throws DataNotFoundException, ParseException, ParseException, InvalidPasswordException;


    CustomerEntity getCustomerDetailsFromToken(String token) throws Exception;
    CustomerEntity getCustomerDetailsFromRefreshToken(String token) throws Exception;
    List<CustomerEntity> getAllCustomers() throws Exception;

    CustomerEntity getCustomerById(long id) throws Exception;

    CustomerEntity loadCustomerByPhoneNumber(String phoneNumber) throws DataNotFoundException;

    void changePassword(long customerId, ChangePasswordCustomerDTO customerDTO) throws DataNotFoundException, InvalidPasswordException;
}
