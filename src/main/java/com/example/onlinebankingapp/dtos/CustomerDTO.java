package com.example.onlinebankingapp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

//in charge: dat
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends AbstractUserDTO //data transfer object for customer, this class extends the abstractUserDTO
{

    @JsonProperty("pin_number")
    private Long pinNumber; //customer's pin number

}
