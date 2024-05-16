package com.example.onlinebankingapp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends AbstractUserDTO {

    @JsonProperty("pin_number")
    private Long pinNumber;

}
