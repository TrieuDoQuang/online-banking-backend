package com.example.onlinebankingapp.services.Customer;

import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;

import java.util.List;

public interface CustomerService {

    String login(String phoneNumber, String password) throws Exception;
    CustomerEntity insertCustomer(CustomerEntity customerEntity) throws DataNotFoundException;

    List<CustomerEntity> getAllCustomers() throws Exception;

    CustomerEntity getCustomerById(long id) throws Exception;
}
