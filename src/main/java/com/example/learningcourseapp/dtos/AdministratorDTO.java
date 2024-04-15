package com.example.learningcourseapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorDTO {

    private String username;

    private String password;


}
