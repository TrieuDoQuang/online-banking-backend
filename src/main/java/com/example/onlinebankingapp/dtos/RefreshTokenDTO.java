package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
//in charge: trieu
@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenDTO {
    // Field for refresh token with JSON property name annotation
    @JsonProperty("refresh_token")
    private String refreshToken;
}
