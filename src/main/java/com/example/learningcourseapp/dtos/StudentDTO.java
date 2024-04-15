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
public class StudentDTO {


    private String username;

    private String password;

    @Column (name ="is_vip")
    private boolean isVip;

    private String name;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    private Double balance;

    private String email;

    @JsonProperty("phone_number")
    private Long phoneNumber;

//    private List<CourseDTO> courses;
//
//    private List<CommentDTO> comments;
//
//    private List<RatingDTO> ratings;

}
