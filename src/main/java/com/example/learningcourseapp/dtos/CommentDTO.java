package com.example.learningcourseapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {


    private String content;

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("student_id")
    private Long studentId;

}
