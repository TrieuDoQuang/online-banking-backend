package com.example.learningcourseapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

// import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "chapters")
public class ChapterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Column(name = "title")
    private String title;

}
