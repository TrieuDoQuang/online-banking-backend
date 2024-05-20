package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAccountDTO {
    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_status")
    private String accountStatus;

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("dateClosed")
    private Date dateClosed;

    @JsonProperty("dateOpened")
    private Date dateOpened;
}
