package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class OTPRequest {

    @JsonProperty("receiver_email")
    private String receiverEmail;

}
