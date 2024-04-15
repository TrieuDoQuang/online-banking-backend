package com.example.learningcourseapp.dtos;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "rating_value")
    private long ratingValue;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseDTO course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentDTO student;
}
