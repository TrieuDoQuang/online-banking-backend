package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String username;

    private String password;

    private String name;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    private String email;

    @JsonProperty("phone_number")
    private Long phoneNumber;
}
