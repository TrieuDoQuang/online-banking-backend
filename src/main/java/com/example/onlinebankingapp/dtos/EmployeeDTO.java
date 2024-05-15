package com.example.onlinebankingapp.dtos;

import com.example.onlinebankingapp.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    @JsonProperty("address")
    @NotBlank(message = "Address is required")
    private String address;

    @JsonProperty("citizenId")
    @NotBlank(message = "Citizen Id is required")
    private String citizenid;

    @JsonProperty("dateOfBirth")
    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    private String email;

    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    private String name;

    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    private String password;

    @JsonProperty("phonenumber")
    @NotBlank(message = "Phone number is required")
    private String phonenumber;

    @JsonProperty("role")
    @NotBlank(message = "Role is required")
    private String role;
}
