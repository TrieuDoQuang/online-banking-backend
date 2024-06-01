package com.example.onlinebankingapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
//in charge: dat
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseObject //Response Object to set the body data content of the response
{

    @JsonProperty("message")
    private String message; // Field for message with JSON property name annotation

    @JsonProperty("status")
    private HttpStatus status; // Field for status with JSON property name annotation

    @JsonProperty("result")
    private Object result; // Field for status with JSON property name annotation

}
