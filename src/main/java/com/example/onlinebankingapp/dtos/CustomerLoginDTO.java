package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDTO {
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Password cannot blank")
    private String password;
}
