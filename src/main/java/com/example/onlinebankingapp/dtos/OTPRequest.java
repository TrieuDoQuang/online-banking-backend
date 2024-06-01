package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
//in charge: dat
@Data
public class OTPRequest //dto for otp request
{

    @JsonProperty("receiver_email")
    private String receiverEmail;

}
