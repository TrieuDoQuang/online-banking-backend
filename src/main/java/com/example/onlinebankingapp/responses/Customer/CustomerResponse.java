package com.example.onlinebankingapp.responses.Customer;


import com.example.onlinebankingapp.entities.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    private String password;

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

    public static CustomerResponse fromUserResponse(CustomerEntity customerEntity) {
        return CustomerResponse
                .builder()
                .id(customerEntity.getId())
                .email(customerEntity.getEmail())
                .name(customerEntity.getName())
                .password(customerEntity.getPassword())
                .phoneNumber(customerEntity.getPhoneNumber())
                .address(customerEntity.getAddress())
                .citizenId(customerEntity.getCitizenId())
                .dateOfBirth(customerEntity.getDateOfBirth())
                .pinNumber(customerEntity.getPinNumber())
                .build();
    }

}
