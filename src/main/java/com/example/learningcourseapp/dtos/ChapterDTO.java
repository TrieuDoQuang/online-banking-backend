package com.example.learningcourseapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

// import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chapters")
public class ChapterDTO {

    @JsonProperty("course_id")
    private Long courseId;

    private String title;

}
