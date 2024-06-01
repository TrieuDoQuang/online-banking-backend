package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
//in charge: trieu + dat
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractUserDTO // abstract data transfer object class for user
{

    private String name; // User's name

    private String password; // User's password

    @JsonProperty("date_of_birth")
    private Date dateOfBirth; // JSON property for date of birth

    private String email; // User's email address

    private String address; // User's address

    @JsonProperty("phone_number")
    private String phoneNumber; // JSON property for phone number

    @JsonProperty("citizen_id")
    private String citizenId; // JSON property for citizen ID
}
