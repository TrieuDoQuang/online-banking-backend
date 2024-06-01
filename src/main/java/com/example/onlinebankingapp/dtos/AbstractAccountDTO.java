package com.example.onlinebankingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Date;
//in charge: dat + trieu
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAccountDTO //abstract data transfer object class for account
{
    // JSON property for account number
    @JsonProperty("account_number")
    private String accountNumber;

    // JSON property for account status
    @JsonProperty("account_status")
    private String accountStatus;

    // JSON property for account type
    @JsonProperty("account_type")
    private String accountType;

    // JSON property for date when the account was closed
    @JsonProperty("dateClosed")
    private Date dateClosed;

    // JSON property for date when the account was opened
    @JsonProperty("dateOpened")
    private Date dateOpened;

}
