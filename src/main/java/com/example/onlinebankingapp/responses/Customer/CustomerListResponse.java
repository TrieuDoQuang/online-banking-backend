package com.example.onlinebankingapp.responses.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class CustomerListResponse {

    private List<CustomerResponse> users;
    private int totalPages;

}
