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
@Table(name ="comments")

public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "content")
    private String content;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
}
