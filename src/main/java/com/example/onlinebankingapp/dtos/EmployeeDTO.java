package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

// not implemented
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO extends AbstractUserDTO {

    @JsonProperty("role")
    private String role;
}
