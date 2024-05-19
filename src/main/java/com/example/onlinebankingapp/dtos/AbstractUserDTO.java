package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractUserDTO {

    private String name;

    private String password;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    private String email;

    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("citizen_id")
    private String citizenId;
}
