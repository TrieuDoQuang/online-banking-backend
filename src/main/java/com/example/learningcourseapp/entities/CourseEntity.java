package com.example.learningcourseapp.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="courses")

public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "discount")
    private int discount;

    @Column (name = "description")
    private String description;

    @Column (name = "title")
    private String title;

    @Column (name = "cost")
    private double cost;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;

}
