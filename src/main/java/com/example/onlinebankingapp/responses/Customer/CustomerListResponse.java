package com.example.onlinebankingapp.responses.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//in charge: trieu
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class CustomerListResponse//custom response for list of customers
{

    private List<CustomerResponse> users;
    private int totalPages;

}
