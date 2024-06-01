package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
//in charge: trieu
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDTO //dto for log in
{
    // Email field with JSON property name annotation
    @JsonProperty("email")
    private String email;

    // Password field with validation for not being blank
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
