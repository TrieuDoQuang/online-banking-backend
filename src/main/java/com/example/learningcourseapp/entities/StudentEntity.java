package com.example.learningcourseapp.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="students")

public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name ="username")
    private String username;
    @Column (name ="password")
    private String password;
    @Column (name ="date_of_birth")
    private Date dateOfBirth;
    @Column (name ="email")
    private String email;
    @Column (name ="is_vip")
    private boolean isVip;
    @Column (name ="name")
    private String name;
    @Column (name = "phone_number")
    private long phoneNumber;
    @Column (name = "balance")
    private long balance;

    @ManyToMany (targetEntity = CourseEntity.class)
    private List<CourseEntity> courses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;

}
