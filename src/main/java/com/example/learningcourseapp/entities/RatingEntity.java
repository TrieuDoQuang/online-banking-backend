package com.example.learningcourseapp.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="ratings")

public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "rating_value")
    private long ratingValue;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
}
