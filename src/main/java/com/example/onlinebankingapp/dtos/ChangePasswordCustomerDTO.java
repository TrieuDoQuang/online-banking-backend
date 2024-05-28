package com.example.onlinebankingapp.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordCustomerDTO {



    @NotBlank(message =  "Password cannot be blank")
    private String password;

    @NotBlank(message = "New password cannot be blank")
    @JsonProperty("new_password")
    private String newPassword;

    @NotBlank(message = "Confirm password cannot be blank")
    @JsonProperty("confirm_password")
    private String confirmPassword;

}
