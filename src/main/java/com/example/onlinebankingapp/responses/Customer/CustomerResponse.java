package com.example.onlinebankingapp.responses.Customer;


import com.example.onlinebankingapp.entities.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
//in charge: trieu
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse //custom response for customer data
{

    //customer data fields
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("citizen_id")
    private String citizenId;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("pin_number")
    private Long pinNumber;

    // Static method to create a CustomerResponse object from a CustomerEntity object
    public static CustomerResponse fromCustomerResponse(CustomerEntity customerEntity) {
        return CustomerResponse
                .builder()
                .id(customerEntity.getId())
                .email(customerEntity.getEmail())
                .name(customerEntity.getName())
                .phoneNumber(customerEntity.getPhoneNumber())
                .address(customerEntity.getAddress())
                .citizenId(customerEntity.getCitizenId())
                .dateOfBirth(customerEntity.getDateOfBirth())
                .pinNumber(customerEntity.getPinNumber())
                .build();
    }

}
