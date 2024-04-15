package com.example.learningcourseapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDTO {

    private String name;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    private Double balance;

    private String email;

    @JsonProperty("phone_number")
    private Long phoneNumber;

    @OneToMany (targetEntity = CourseDTO.class)
    private List<CourseDTO> courses;
}
