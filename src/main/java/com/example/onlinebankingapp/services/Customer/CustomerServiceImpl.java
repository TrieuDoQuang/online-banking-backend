package com.example.onlinebankingapp.services.Customer;

import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    @Override
    public String login(String phoneNumber, String password) throws Exception {
        return null;
    }

    @Override
    public CustomerEntity insertCustomer(CustomerEntity customerEntity) throws DataNotFoundException {
        return null;
    }

    @Override
    public List<CustomerEntity> getAllCustomers() throws Exception {
        return null;
    }

    @Override
    public CustomerEntity getCustomerById(long id) throws Exception {
        return null;
    }
}
